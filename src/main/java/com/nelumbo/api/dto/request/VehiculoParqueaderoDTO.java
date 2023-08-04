package com.nelumbo.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehiculoParqueaderoDTO {

    private Long Id;

    @NotNull(message = "no puede ser nulo")
    @Min(value = 0, message = "Debe ser un número válido mayor o igual a 0")
    private Long idParqueadero;
    
    @Length(min = 6, max = 6)
    @NotNull(message = "Campo requerido")
    @NotBlank(message = "El campo no puede ser vacio")
    @Pattern(regexp = "^[a-zA-Z0-9&&[^ñ]]*$", message = "No se permiten caracteres especiales ni la letra 'ñ'")
    private String placa;
}
