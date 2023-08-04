package com.nelumbo.api.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ParqueaderoResponse {
    private Long id;
    private String nombre;
    private Long cantidadVehiculos;
    private Double costo;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaUpdate;
}
