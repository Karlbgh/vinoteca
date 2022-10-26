package es.carlosgh.vinoteca.controller;

import es.carlosgh.vinoteca.service.VinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private VinoService servicio;

    @GetMapping({"", "index"})
    public String welcome(Model model){
        model.addAttribute("listaVino", servicio.findAll() );
        return "index";
    }
}
