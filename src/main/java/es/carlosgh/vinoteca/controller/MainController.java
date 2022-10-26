package es.carlosgh.vinoteca.controller;

import es.carlosgh.vinoteca.service.VinoService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Data
@RequestMapping("/")
public class MainController {

    private final VinoService servicio;

    @GetMapping({"", "index"})
    public String welcome(Model model){
        model.addAttribute("listaVino", servicio.findAll() );
        return "index";
    }
}
