package com.nelumbo.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VehiculoDTO {
    private Long id;

    @Length(min = 6, max = 6)
    @NotNull(message = "Campo requerido")
    @NotBlank(message = "El campo no puede ser vacio")
    private String placa;

}
