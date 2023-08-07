package com.nelumbo.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ParqueaderoDTO {
    private Long id;

    @NotNull
    @NotBlank(message = "Nombre requerido")
    private String nombre;

    @NotNull(message = "no puede ser nulo")
    @Min(value = 0, message = "Debe ser un número válido mayor o igual a 0")
    private Long cantidadVehiculos;

    @NotNull(message = "no puede ser nulo")
    @Min(value = 1, message = "Debe ser un número válido mayor o igual a 0")
    private Double costo;

}
