package es.carlosgh.vinoteca.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;

public class Util {

    public static HashMap<String, Boolean> comprobarLogin() {
        HashMap<String, Boolean> result = new HashMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        String usuario = authentication.getName();


        return result;
    }
}
