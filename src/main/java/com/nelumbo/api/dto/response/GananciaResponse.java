package com.nelumbo.api.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GananciaResponse {

    private String tiempo;
    private Double ganancia;
}
