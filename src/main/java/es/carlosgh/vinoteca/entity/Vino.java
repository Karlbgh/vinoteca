package es.carlosgh.vinoteca.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Vino {
    @NotEmpty
    private Long id;
    @NotEmpty
    private String variedadUva;
    @NotEmpty
    private String origen;
    @NotEmpty
    private String maduracion;
    @NotEmpty
    private String graduacion;
    @NotEmpty
    private String acidez;
    @NotEmpty
    private String imagen;
}
