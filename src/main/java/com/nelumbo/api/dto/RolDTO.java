package com.nelumbo.api.dto;

import lombok.*;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RolDTO {
    private Long id;
    private String nombre;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;
}
