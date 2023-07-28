package com.nelumbo.api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParqueaderoSocio {
    private long id;
    @NotNull(message = "El campo no puede estar vacío.")
    @Min(value = 0, message = "El valor debe ser mayor o igual a 0")
    @Max(value = 100, message = "El valor debe ser menor o igual a 100")
    private Long parqueaderoId;

    @NotNull(message = "El campo no puede estar vacío.")
    @Min(value = 0, message = "El valor debe ser mayor o igual a 0")
    @Max(value = 100, message = "El valor debe ser menor o igual a 100")
    private Long socioId;
}
