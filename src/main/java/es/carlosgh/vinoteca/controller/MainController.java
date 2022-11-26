package es.carlosgh.vinoteca.controller;

import es.carlosgh.vinoteca.entity.Vino;
import es.carlosgh.vinoteca.service.I18nService;
import es.carlosgh.vinoteca.service.VinoService;
import lombok.Data;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
@Data
@RequestMapping("/")
public class MainController {

    private final VinoService servicio;
    private static final String CONTADOR_NAME = "numVisitas";
    private static final String CONTADOR_NAME_INDEX = "numVisitasIndex";
    private final I18nService servicioInternacionalizacion;

    @GetMapping({"", "index"})
    public String welcome(HttpServletRequest request, HttpServletResponse response, Model model){
        List<Vino> vinosMasCaros = servicio.findAll().stream().sorted((x,y)-> y.getPrecio().compareTo(x.getPrecio())).limit(3).toList();
        model.addAttribute("listaVino", vinosMasCaros );

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String usuario = authentication.getName();

            // Si ya hay datos de contador de visitas en la sesión es que no es la primera vez
            // que se pasa por aquí y no incrementamos la cookie
            HttpSession session = request.getSession();
            boolean primeraVez = (session.getAttribute(CONTADOR_NAME) == null);

            if (primeraVez) {
                // Comprobar si el navegador tenía cookie del usuario
                Optional<Cookie> cookieEncontrada = Arrays.stream(request.getCookies())
                        .filter(cookie -> usuario.equals(cookie.getName())).findAny();

                // si no existe la cookie el contador de visitas se pone a 1
                int contador = 1;
                if (cookieEncontrada.isEmpty()) {
                    Cookie cookie = new Cookie(usuario, "1");
                    cookie.setPath("/");
                    cookie.setDomain("localhost");
                    cookie.setSecure(true);
                    cookie.setHttpOnly(true);
                    response.addCookie(cookie);

                } else {  // si existe la cookie se recupera el contador y se le suma 1
                    Cookie cookie = cookieEncontrada.get();
                    contador = Integer.parseInt(cookie.getValue()) + 1;
                    cookie.setValue(String.valueOf(contador));
                    response.addCookie(cookie);
                }

                // Almacenar en session el contador de visitas a la aplicación
                session.setAttribute(CONTADOR_NAME, contador);
            }

            // Almacenar en session el contador de visitas a la aplicación
            Object contadorIndex = session.getAttribute(CONTADOR_NAME_INDEX);
            session.setAttribute(CONTADOR_NAME_INDEX, (contadorIndex == null) ? 1 : (int) contadorIndex + 1);
        }

        String textNumLogin =  servicioInternacionalizacion.getMessage("header.menu.numLoginUser");
        String textNumIndex =  servicioInternacionalizacion.getMessage("header.menu.numVisitasIndex");
        model.addAttribute("numLogin",textNumLogin);
        model.addAttribute("textNumIndex",textNumIndex);

        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "loginRegistro";
    }
    @GetMapping("/error")
    public String loginError(){
        return "loginRegistro";
    }

}
