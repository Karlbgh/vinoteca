package es.carlosgh.vinoteca.entidades;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Vino {
    @GeneratedValue
    @Id
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
