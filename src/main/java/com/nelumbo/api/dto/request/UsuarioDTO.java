package com.nelumbo.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioDTO {
    private Long id;

    @NotNull (message = "Campo Nombre es requerido")
    @NotBlank(message = "Campo Nombre no puede estar vacio")
    private String nombre;

    @NotNull(message = "Campo Email es requerido")
    @Email
    private String email;

    @NotNull(message = "Campo Contraseña es requerido")
    @NotBlank(message = "Campo Contraseña no debe estar vacio")
    private String pass;

    @NotNull(message = "Campo Rol es Requerido")
    private Long rolId;
}
