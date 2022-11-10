package es.carlosgh.vinoteca.service;

import es.carlosgh.vinoteca.entity.RolUsuario;
import es.carlosgh.vinoteca.entity.Usuario;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UsuarioService {

    private List<Usuario> userRepository = new ArrayList<>();

    public Boolean add(Usuario usuario){
        userRepository.add(usuario);
        return userRepository.contains(usuario);
    }

    public Boolean remove(Usuario usuario){
        userRepository.remove(usuario);
        return  !userRepository.contains(usuario);
    }

    public Boolean edit(Usuario usuario){
        Usuario mUsuario = findById(usuario.getId());
        if (mUsuario != null){
            remove(mUsuario);
        }
        add(mUsuario);
        return userRepository.contains(mUsuario);
    }

    public Usuario findById(long id){
        if (userRepository.size() > 0){
            for (Usuario usuario: userRepository) {
                if (usuario.getId().equals(id)){
                    return usuario;
                }
            }
        }
        return null;
    }

    public List<Usuario> findAll(){
        return userRepository;
    }

    @PostConstruct
    public void fillDefaultUsers(){
        userRepository.addAll(
                Arrays.asList(
                        Usuario.builder()
                                .id(1L)
                                .name("carlos")
                                .password("1234")
                                .mail("carlos@carlos.com")
                                .rol(RolUsuario.ADMIN)
                                .build(),
                        Usuario.builder()
                                .id(2L)
                                .name("user")
                                .password("1234")
                                .mail("user@user.com")
                                .rol(RolUsuario.USER)
                                .build()
                )
        );
    }

}
