package com.nelumbo.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailRequest {
    @NotNull(message = "Campo requerido")
    @NotBlank(message = "Campo requerido")
    private String email;
    @NotNull(message = "Campo requerido")
    @NotBlank(message = "Campo requerido")
    private String placa;
    @NotNull(message = "Campo requerido")
    @NotBlank(message = "Campo requerido")
    private String mensaje;
    @NotNull(message = "Campo requerido")
    @NotBlank(message = "Campo requerido")
    private String parqueaderoNombre;

}
