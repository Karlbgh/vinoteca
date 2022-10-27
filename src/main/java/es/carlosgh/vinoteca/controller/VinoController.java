package es.carlosgh.vinoteca.controller;

import es.carlosgh.vinoteca.entity.Vino;
import es.carlosgh.vinoteca.service.VinoService;
import es.carlosgh.vinoteca.storage.StorageService;
import lombok.Data;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;

@Data
@Controller
@RequestMapping("/vino")
public class VinoController {

    private final VinoService servicio;
    private final StorageService storageService;
    @GetMapping("/lista")
    public String listaDeVinos(Model model){
        model.addAttribute("listaVinos", servicio.findAll());
        return "lista";
    }
    @GetMapping("/editar/{id}")
    public String editarONuevoForm(@PathVariable long id, Model model){
        Vino vino = servicio.findById(id);
        if ( vino != null){
            model.addAttribute("vinoFormulario", vino);
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
        Long nID = servicio.findAll().stream().max((x, y) -> x.getId().compareTo(y.getId())).get().getId()+1;
        model.addAttribute("vinoFormulario", new Vino(nID));
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
}
