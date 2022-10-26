package es.carlosgh.vinoteca.service;

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
                                .uva("pocha")
                                .origen("casero")
                                .graduacion("10")
                                .year("22/10/2019")
                                .imagen("img1")
                                .descripcion("descripcion vino 1")
                                .build(),
                        Vino.builder()
                                .id(2L)
                                .uva("bonita")
                                .origen("de carton")
                                .graduacion("13")
                                .year("22/01/2010")
                                .imagen("img2")
                                .descripcion("descripcion vino 2")
                                .build(),
                        Vino.builder()
                                .id(3L)
                                .uva("la morada")
                                .origen("del campo")
                                .graduacion("9")
                                .year("02/05/2000")
                                .imagen("img3")
                                .descripcion("descripcion vino 3")
                                .build()
                )
        );
    }
}
