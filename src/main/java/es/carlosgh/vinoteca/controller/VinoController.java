package es.carlosgh.vinoteca.controller;

import es.carlosgh.vinoteca.entity.Vino;
import es.carlosgh.vinoteca.service.VinoService;
import es.carlosgh.vinoteca.storage.StorageService;
import lombok.Data;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Controller
@RequestMapping("/vino")
public class VinoController {

    private final VinoService servicio;
    private final StorageService storageService;
    @GetMapping("/lista")
    public String listaDeVinos(Model model){
        List<Vino> listaOrdenada = servicio.findAll().stream().sorted(Comparator.comparing(Vino::getId)).toList();
        model.addAttribute("listaVinos", listaOrdenada);
        return "lista";
    }
    @GetMapping("/editar/{id}")
    public String editarONuevoForm(@PathVariable long id, Model model){
        Vino vino = servicio.findById(id);
        if ( vino != null){
            model.addAttribute("vinoFormulario", vino);
            model.addAttribute("fromEdit", true);
            return "formulario";
        }
        return "redirect:/vino/lista";
    }
    @PostMapping("/editar/submit")
    public String editarVinoSubmit(@Valid @ModelAttribute("vinoFormulario") Vino vino, BindingResult bindingResult, @RequestParam("file") MultipartFile file){
        if (bindingResult.hasErrors()){
            return "formulario";
        }

        if (!file.isEmpty()) {
            String imgVino = storageService.store(file, vino.getId());
            vino.setImagen(MvcUriComponentsBuilder.fromMethodName(VinoController.class, "serveFile", imgVino).build().toUriString());
        }
        servicio.edit(vino);
        return "redirect:/vino/lista";
    }
    @GetMapping("/nuevo")
    public String nuevoVinoForm(Model model) {
        Long nID = servicio.findAll().stream().max(Comparator.comparing(Vino::getId)).get().getId()+1;
        model.addAttribute("vinoFormulario", new Vino(nID));
        model.addAttribute("fromEdit", false);
        return "formulario";
    }
    @GetMapping("/borrar/{id}")
    public String borrarVino(@PathVariable("id") Long id, Model model){
        Vino vino = servicio.findById(id);
        if (vino != null ){
            servicio.remove(vino);
        }
        return "redirect:/vino/lista";
    }
    @ResponseBody
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().body(file);
    }
    @GetMapping("/lista/filtro")
    public String filtrarPorPrecio(@RequestParam("filtro") String filtro, Model model, HttpServletResponse response, HttpServletRequest request){
        Double precio;
        List<Vino> vinosFiltrados;
        try {
            precio = Double.parseDouble(filtro);
            vinosFiltrados = servicio.findAll().stream().filter(x -> x.getPrecio().equals(precio)).collect(Collectors.toList());
            model.addAttribute("listaVinos", vinosFiltrados);

            String usuario = "";
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)){
                usuario = authentication.getName();

                Cookie cookie = new Cookie(usuario+"_busc_"+precio, precio.toString());
                cookie.setPath("/");
                cookie.setDomain("localhost");
                cookie.setMaxAge(30 * 24 * 60 * 60); // 30 días
                cookie.setSecure(true);
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }

        }catch (NumberFormatException  e){
            vinosFiltrados = servicio.findAll();
            model.addAttribute("listaVinos", vinosFiltrados );
        }

        return "fragments/tablaVinos_fragment::tablaVinos";
    }
}
