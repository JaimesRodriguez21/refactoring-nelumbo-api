package com.nelumbo.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ComentarioDTO {

    private Long id;

    @NotNull(message = "Campo requerido")
    @NotBlank(message = "Campo vacio")
    private String nombre;

    @NotNull(message = "Campo requerido")
    @NotBlank(message = "Campo vacio")
    private String contenido;

}
