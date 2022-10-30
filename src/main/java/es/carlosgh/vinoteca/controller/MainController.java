package es.carlosgh.vinoteca.controller;

import es.carlosgh.vinoteca.entity.Vino;
import es.carlosgh.vinoteca.service.VinoService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@Data
@RequestMapping("/")
public class MainController {

    private final VinoService servicio;

    @GetMapping({"", "index"})
    public String welcome(Model model){
        List<Vino> vinosMasCaros = servicio.findAll().stream().sorted((x,y)-> y.getPrecio().compareTo(x.getPrecio())).limit(3).toList();
        model.addAttribute("listaVino", vinosMasCaros );
        return "index";
    }
}
