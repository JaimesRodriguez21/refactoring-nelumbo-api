package com.nelumbo.api.dto.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VehiculoResponse {
    private String placa;
    private Long cantidad;

    public VehiculoResponse() {
    }

    public VehiculoResponse(String placa, Long cantidad) {
        this.placa = placa;
        this.cantidad = cantidad;
    }
}