package es.carlosgh.vinoteca.controller;

import es.carlosgh.vinoteca.entity.Vino;
import es.carlosgh.vinoteca.service.VinoService;
import lombok.Data;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Data
@Controller
@RequestMapping("/vino")
public class VinoController {

    private final VinoService servicio;

    @GetMapping("/list")
    public String listaDeVinos(Model model){
        model.addAttribute("listaVinos", servicio.findAll());
        return "list";
    }
    @GetMapping("/editar/{id}")
    public String editarONuevoForm(@PathVariable long id, Model model){
        Vino vino = servicio.findById(id);
        if ( vino != null){
            model.addAttribute("vinoFormulario", vino);
            return "formulario";
        }
        return "redirect:/vino/list";
    }

    @PostMapping("/editar/submit")
    public String editarVinoSubmit(@Valid @ModelAttribute("vinoFormulario") Vino vino, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "formulario";
        }
        servicio.edit(vino);
        return "redirect:/vino/list";
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
        return "redirect:/vino/list";
    }

}
