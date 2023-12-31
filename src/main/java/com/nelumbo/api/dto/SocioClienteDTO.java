package com.nelumbo.api.dto;

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
public class SocioClienteDTO {

    private Long id;
    @NotNull
    private Long socioId;
    @NotNull
    private Long clienteId;

}
