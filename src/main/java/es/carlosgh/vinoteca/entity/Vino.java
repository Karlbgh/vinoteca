package es.carlosgh.vinoteca.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Vino {

    private Long id;
    @NotEmpty
    private String uva;
    @NotEmpty
    private String origen;
    @NotEmpty
    private String graduacion;
    @NotEmpty
    private String year;

    private String imagen;

    private String descripcion;
}
