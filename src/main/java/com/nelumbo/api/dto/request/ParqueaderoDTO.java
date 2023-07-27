package com.nelumbo.api.dto.request;

import com.nelumbo.api.entity.Vehiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParqueaderoDTO {
    private Long id;

    @NotNull
    @NotBlank
    private String nombre;
    @NotNull
    @NotBlank
    private String registro;

    private Boolean estado;
    private Long CantidadVehiculos;

    private List<Vehiculo> vehiculos;;

    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;
}
