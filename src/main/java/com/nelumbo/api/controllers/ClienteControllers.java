package com.nelumbo.api.controllers;

import com.nelumbo.api.annotations.IngressAllowed;
import com.nelumbo.api.config.SecurityUtils;
import com.nelumbo.api.dto.request.VehiculoDTO;
import com.nelumbo.api.dto.request.VehiculoParqueaderoDTO;
import com.nelumbo.api.dto.response.CreatedResponse;
import com.nelumbo.api.dto.response.Succesfull;
import com.nelumbo.api.entity.Usuario;
import com.nelumbo.api.exception.AccessDeniedException;
import com.nelumbo.api.service.ParqueaderoService;
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
@RequestMapping(value = "/clientes")
public class ClienteControllers {

    @Autowired
    ParqueaderoService parqueaderoService;

    @Autowired
    VehiculoParqueaderoService vehiculoParqueaderoService;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping(value = "ingreso-vehiculo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse RegistrarIngreso(@Valid @RequestBody VehiculoParqueaderoDTO vehiculoParqueaderoDTO) {
        if (SecurityUtils.obtenerRolUsuarioActual().equals("CLIENTE")) {
            vehiculoParqueaderoService.ingresoVehiculo(vehiculoParqueaderoDTO);
        } else {
            throw new AccessDeniedException("Acceso denegado");
        }
        return new CreatedResponse(vehiculoParqueaderoDTO.getId());
    }

    @PostMapping(value = "salida-vehiculo/{idIngreso}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Succesfull RegistrarSalida(@Valid @RequestBody VehiculoDTO vehiculoDTO) {
        if (SecurityUtils.obtenerRolUsuarioActual().equals("CLIENTE")) {
            vehiculoParqueaderoService.salidaVehiculo(vehiculoDTO);
        } else {
            throw new AccessDeniedException("Acceso denegado");
        }
        return new Succesfull("Salida registrada");
    }

    @GetMapping(value = "detalle-vehiculos", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<VehiculoDTO> detalleVehiculos(
    ) {
        if (SecurityUtils.obtenerRolUsuarioActual().equals("CLIENTE")) {
            return vehiculoParqueaderoService.listVehiculos();
        } else {
            throw new AccessDeniedException("Acceso denegado");
        }

    }


    @GetMapping(value = "detalle-vehiculos-socio/{idSocio}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<VehiculoDTO> detalleVehiculosPorSocio(
            @PathVariable(name = "idSocio") Long idSocio
    ) {
        if (SecurityUtils.obtenerRolUsuarioActual().equals("CLIENTE")) {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(idSocio);
            return vehiculoParqueaderoService.listVehiculosPorSocio(usuario);
        } else {
            throw new AccessDeniedException("Acceso denegado");
        }

    }


}
