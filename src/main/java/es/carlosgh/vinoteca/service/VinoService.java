package es.carlosgh.vinoteca.service;

import es.carlosgh.vinoteca.entity.TipoVino;
import es.carlosgh.vinoteca.entity.Vino;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class VinoService {

    private List<Vino> repository = new ArrayList<>();

    public Boolean add(Vino vino){
        repository.add(vino);
        return repository.contains(vino);
    }

    public Boolean remove(Vino vino){
        repository.remove(vino);
        return  !repository.contains(vino);
    }

    public Boolean edit(Vino vino){
        Vino mVino = findById(vino.getId());
        if (mVino != null){
            remove(mVino);
        }
        add(vino);
        return repository.contains(vino);
    }

    public Vino findById(long id){
        if (repository.size() > 0){
            for (Vino vino: repository) {
                if (vino.getId().equals(id)){
                    return vino;
                }
            }
        }
        return null;
    }

    public List<Vino> findAll(){
        return repository;
    }

    @PostConstruct
    public void fillRepository(){
        repository.addAll(
                Arrays.asList(
                        Vino.builder()
                                .id(1L)
                                .tipo(TipoVino.Blanco)
                                .precio(25.6)
                                .graduacion("10")
                                .year("1990")
                                .imagen("/img/img1.jpg")
                                .descripcion("descripcion del vino 1")
                                .build(),
                        Vino.builder()
                                .id(2L)
                                .tipo(TipoVino.Tinto)
                                .precio(80.0)
                                .graduacion("13")
                                .year("2010")
                                .imagen("/img/img2.jpg")
                                .descripcion("descripcion del vino 2")
                                .build(),
                        Vino.builder()
                                .id(3L)
                                .tipo(TipoVino.Rosado)
                                .precio(35.3)
                                .graduacion("9")
                                .year("2000")
                                .imagen("/img/img3.jpg")
                                .descripcion("descripcion del vino 3")
                                .build()
                )
        );
    }
}
