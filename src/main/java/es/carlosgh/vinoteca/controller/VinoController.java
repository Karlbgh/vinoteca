package es.carlosgh.vinoteca.controller;

import es.carlosgh.vinoteca.service.VinoService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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



}
