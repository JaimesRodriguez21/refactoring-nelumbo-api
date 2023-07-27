package com.nelumbo.api.dto.request;

import com.nelumbo.api.dto.request.ParqueaderoDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehiculoDTO {
    private Long id;
    @Length(min = 6, max = 6)
    @NotNull
    @NotBlank
    private String placa;
    @NotNull
    @NotBlank
    private String modelo;
    private ParqueaderoDTO parqueadero;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;
}
