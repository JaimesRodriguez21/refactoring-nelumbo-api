package com.nelumbo.api.service;

import com.nelumbo.api.dto.request.SocioClienteDTO;
import jakarta.validation.Valid;

public interface SocioClienteService {
    SocioClienteDTO asociarClienteConSocios(SocioClienteDTO socioClienteDTO);
}
