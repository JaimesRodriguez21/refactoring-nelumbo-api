package com.nelumbo.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotNull(message = "Campo Email es requerido")
    @Email
    private String email;

    @NotNull(message = "Campo Nombre es requerido")
    @NotBlank(message = "Campo Nombre no puede estar vacio")
    private String nombre;

    @NotNull(message = "Campo Contraseña es requerido")
    @NotBlank(message = "Campo Contraseña no debe estar vacio")
    private String pass;


    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;

}