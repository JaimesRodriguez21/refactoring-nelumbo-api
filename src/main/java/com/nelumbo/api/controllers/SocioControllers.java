package com.nelumbo.api.controllers;

import com.nelumbo.api.config.SecurityUtils;
import com.nelumbo.api.dto.request.SocioClienteDTO;
import com.nelumbo.api.dto.response.CreatedResponse;
import com.nelumbo.api.entity.Usuario;
import com.nelumbo.api.exception.AccessDeniedException;
import com.nelumbo.api.repository.UsuarioRepository;
import com.nelumbo.api.service.SocioClienteService;
import com.nelumbo.api.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/socios")
public class SocioControllers {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    SocioClienteService socioClienteService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping(value = "cliente-socio", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse asocirClienteASocios(@Valid @RequestBody SocioClienteDTO socioClienteDTO) {
        if (SecurityUtils.obtenerRolUsuarioActual().equals("ADMIN")) {
            socioClienteService.asociarClienteConSocios(socioClienteDTO);
        } else {
            throw new AccessDeniedException("acceso denegado");
        }
        return new CreatedResponse(socioClienteDTO.getId());
    }

    @PostMapping(value = "clientes/{idCliente}/socio", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse asocirClienteASocio(
            @PathVariable(name = "idCliente") @Size(min = 0) long idCliente
    ) {
        System.out.println(idCliente);
        SocioClienteDTO socioClienteDTO = new SocioClienteDTO();
        if (SecurityUtils.obtenerRolUsuarioActual().equals("SOCIO")) {
            Optional<Usuario> usuario = usuarioRepository.findByEmail(SecurityUtils.obtenerUsernameUser());
            socioClienteDTO.setClienteId(idCliente);
            socioClienteDTO.setSocioId(usuario.get().getId());
            socioClienteService.asociarClienteConSocios(socioClienteDTO);
        } else {
            throw new AccessDeniedException("acceso denegado");
        }
        return new CreatedResponse(socioClienteDTO.getId());
    }
}
