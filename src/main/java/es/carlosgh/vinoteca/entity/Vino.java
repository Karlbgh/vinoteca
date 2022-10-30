package es.carlosgh.vinoteca.entity;

import es.carlosgh.vinoteca.service.VinoService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
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

    @NotNull(message="{vino.precio.vacio}")
    private Double precio;

    @NotEmpty(message="{vino.graduacion.vacio}")
    private String graduacion;

    @NotEmpty(message="{vino.year.vacio}")
    private String year;

    private String imagen;

    @NotEmpty(message="{vino.desc.vacio}")
    @Length( max = 255, min = 20 ,message = "{vino.desc.max}")
    private String descripcion;

    public Vino(Long id) {
        this.id = id;
    }
}
