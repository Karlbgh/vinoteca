package es.carlosgh.vinoteca.service;

import es.carlosgh.vinoteca.entity.Vino;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
                                .variedadUva("pocha")
                                .origen("casero")
                                .maduracion("yogurin")
                                .graduacion("10")
                                .acidez("3,8")
                                .imagen("img1")
                                .descripcion("descripcion vino 1")
                                .build(),
                        Vino.builder()
                                .id(2L)
                                .variedadUva("bonita")
                                .origen("de carton")
                                .maduracion("madurito")
                                .graduacion("13")
                                .acidez("3,10")
                                .imagen("img2")
                                .descripcion("descripcion vino 2")
                                .build(),
                        Vino.builder()
                                .id(3L)
                                .variedadUva("la morada")
                                .origen("del campo")
                                .maduracion("jovencito")
                                .graduacion("9")
                                .acidez("3,5")
                                .imagen("img3")
                                .descripcion("descripcion vino 3")
                                .build()
                )
        );
    }
}
