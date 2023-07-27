package com.nelumbo.api.service.impl;

import com.nelumbo.api.dto.request.SocioClienteDTO;
import com.nelumbo.api.entity.SocioCliente;
import com.nelumbo.api.entity.Usuario;
import com.nelumbo.api.exception.NotFoundException;
import com.nelumbo.api.exception.RegistroDuplicadoException;
import com.nelumbo.api.repository.SocioClienteRepository;
import com.nelumbo.api.repository.UsuarioRepository;
import com.nelumbo.api.service.SocioClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SocioClienteServicioImpl implements SocioClienteService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SocioClienteRepository socioClienteRepository;

    @Override
    public SocioClienteDTO asociarClienteConSocios(SocioClienteDTO socioClienteDTO) {

        Optional<Usuario> optionalUsuarioSocio = usuarioRepository.findById(socioClienteDTO.getSocioId());
        if (!optionalUsuarioSocio.isPresent()) {
            throw new NotFoundException("Socio con id: " + socioClienteDTO.getSocioId() + " no encontrado");
        }
        Optional<Usuario> optionalUsuarioCliente = usuarioRepository.findById(socioClienteDTO.getClienteId());
        if (!optionalUsuarioCliente.isPresent()) {
            throw new NotFoundException("Cliente con id: " + socioClienteDTO.getClienteId() + " no encontrado");
        }

        Optional<SocioCliente> socioCliente = socioClienteRepository.findBySocioIdAndClienteId(socioClienteDTO.getSocioId(), socioClienteDTO.getClienteId());
        if (socioCliente.isPresent()) {
            throw new RegistroDuplicadoException("El cliente con id: " + socioClienteDTO.getClienteId() + " ya esta asociado");
        }

        SocioCliente nuevoSocioCliente = SocioCliente.builder()
                .socio(optionalUsuarioSocio.get())
                .cliente(optionalUsuarioCliente.get())
                .build();

        socioClienteRepository.save(nuevoSocioCliente);
        socioClienteDTO.setId(nuevoSocioCliente.getId());
        return socioClienteDTO;
    }
}
