package com.nelumbo.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehiculoParqueaderoDTO {

    private Long Id;

    @NotNull(message = "no puede ser nulo")
    @Min(value = 0, message = "Debe ser un número válido mayor o igual a 0")
    private Long idParqueadero;

    @NotNull(message = "no puede ser nulo")
    @Min(value = 0, message = "Debe ser un número válido mayor o igual a 0")
    private Long idVehiculo;
}
