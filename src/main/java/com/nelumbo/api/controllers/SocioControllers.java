package com.nelumbo.api.controllers;

import com.nelumbo.api.config.SecurityUtils;
import com.nelumbo.api.dto.request.SocioClienteDTO;
import com.nelumbo.api.dto.request.VehiculoDTO;
import com.nelumbo.api.dto.response.CreatedResponse;
import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.entity.Usuario;
import com.nelumbo.api.exception.AccessDeniedException;
import com.nelumbo.api.service.ParqueaderoService;
import com.nelumbo.api.service.SocioClienteService;
import com.nelumbo.api.service.UsuarioService;
import com.nelumbo.api.service.VehiculoParqueaderoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/socios")
public class SocioControllers {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ParqueaderoService parqueaderoService;

    @Autowired
    SocioClienteService socioClienteService;

    @Autowired
    VehiculoParqueaderoService vehiculoParqueaderoService;

    @PostMapping(value = "cliente-socio", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse asociarClienteASocios(@Valid @RequestBody SocioClienteDTO socioClienteDTO) {
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
            Usuario usuario = usuarioService.obtenerUsuarioPorEmail(SecurityUtils.obtenerUsernameUser());
            socioClienteDTO.setClienteId(idCliente);
            socioClienteDTO.setSocioId(usuario.getId());
            socioClienteService.asociarClienteConSocios(socioClienteDTO);
        } else {
            throw new AccessDeniedException("acceso denegado");
        }
        return new CreatedResponse(socioClienteDTO.getId());
    }

    @GetMapping(value = "detalle-vehiculos", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<VehiculoDTO> detalleVehiculos() {
        if (SecurityUtils.obtenerRolUsuarioActual().equals("SOCIO")) {
            Usuario socio = usuarioService.obtenerUsuarioPorEmail(SecurityUtils.obtenerUsernameUser());
            return vehiculoParqueaderoService.listVehiculosPorSocio(socio);
        } else {
            throw new AccessDeniedException("acceso denegado");
        }
    }
}
