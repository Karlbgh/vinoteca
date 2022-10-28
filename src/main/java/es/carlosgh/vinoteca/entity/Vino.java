package es.carlosgh.vinoteca.entity;

import es.carlosgh.vinoteca.service.VinoService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Vino {

    private Long id;
    @NotNull(message="{vino.tipo.vacio}")
    private TipoVino tipo;
    @NotEmpty(message="{vino.tipo.vacio}")
    private String origen;
    @NotEmpty(message="{vino.tipo.vacio}")
    private String graduacion;
    @NotEmpty(message="{vino.tipo.vacio}")
    private String year;
    @NotEmpty(message="{vino.tipo.vacio}")
    private String imagen;
    @NotEmpty(message="{vino.tipo.vacio}")
    private String descripcion;

    public Vino(Long id) {
        this.id = id;
    }
}
