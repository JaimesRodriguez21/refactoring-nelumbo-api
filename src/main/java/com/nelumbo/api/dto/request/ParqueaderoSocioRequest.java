package com.nelumbo.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ParqueaderoSocioRequest {

    @NotNull(message = "no puede ser nulo")
    @Min(value = 0, message = "Debe ser un número válido mayor o igual a 0")
    private long parqueaderoId;
    @NotNull(message = "no puede ser nulo")
    @Min(value = 0, message = "Debe ser un número válido mayor o igual a 0")
    private long socioId;
}
