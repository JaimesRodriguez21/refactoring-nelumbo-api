package com.nelumbo.api.controllers;

import com.nelumbo.api.annotations.IngressAllowed;
import com.nelumbo.api.config.SecurityUtils;
import com.nelumbo.api.dto.ParqueaderoDTO;
import com.nelumbo.api.dto.VehiculoDTO;
import com.nelumbo.api.dto.VehiculoParqueaderoDTO;
import com.nelumbo.api.dto.response.CreatedResponse;
import com.nelumbo.api.dto.response.GananciaResponse;
import com.nelumbo.api.dto.response.SuccesfullResponse;
import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.entity.Usuario;
import com.nelumbo.api.service.ParqueaderoService;
import com.nelumbo.api.service.SocioClienteService;
import com.nelumbo.api.service.UsuarioService;
import com.nelumbo.api.service.VehiculoParqueaderoService;
import jakarta.validation.Valid;
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

    @IngressAllowed({"SOCIO"})
    @PostMapping(path = "ingreso-vehiculo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse RegistrarIngreso(@Valid @RequestBody VehiculoParqueaderoDTO vehiculoParqueaderoDTO) {
        Usuario socio = usuarioService.obtenerUsuarioPorEmail(SecurityUtils.obtenerUsernameUser());
        parqueaderoService.buscarParqueaderoPorSocioAndId(socio, vehiculoParqueaderoDTO.getIdParqueadero());
        vehiculoParqueaderoService.ingresoVehiculo(vehiculoParqueaderoDTO);
        return new CreatedResponse(vehiculoParqueaderoDTO.getId());
    }

    @IngressAllowed({"SOCIO"})
    @PostMapping(path = "salida-vehiculo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SuccesfullResponse RegistrarSalida(@Valid @RequestBody VehiculoDTO vehiculoDTO) {
        vehiculoParqueaderoService.salidaVehiculo(vehiculoDTO);
        return new SuccesfullResponse("Salida registrada");
    }

    @IngressAllowed({"SOCIO"})
    @GetMapping(path = "parqueaderos", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ParqueaderoDTO> listarParqueaderos() {
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(SecurityUtils.obtenerUsernameUser());
        return parqueaderoService.parqueaderosPorSocio(usuario);
    }

    @IngressAllowed({"SOCIO"})
    @GetMapping(path = "vehiculos-parqueadero/{idParqueadero}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<VehiculoDTO> detalleVehiculosPorParqueadero(
            @PathVariable(value = "idParqueadero") Long idParqueadero) {
        Usuario socio = usuarioService.obtenerUsuarioPorEmail(SecurityUtils.obtenerUsernameUser());
        Parqueadero parqueadero = parqueaderoService.buscarParqueaderoPorSocioAndId(socio, idParqueadero);
        return vehiculoParqueaderoService.listVehiculosPorParqueadero(parqueadero);
    }

    @IngressAllowed({"SOCIO"})
    @GetMapping(path = "ganancias-parqueadero/{idParqueadero}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GananciaResponse obtenerGanancias(
            @PathVariable(value = "idParqueadero") Long idParqueadero,
            @RequestParam(defaultValue = "dia") String tiempo
    ) {
        Usuario socio = usuarioService.obtenerUsuarioPorEmail(SecurityUtils.obtenerUsernameUser());
        Parqueadero parqueadero = parqueaderoService.buscarParqueaderoPorSocioAndId(socio, idParqueadero);
        return vehiculoParqueaderoService.gananciaParqueadero(tiempo, parqueadero.getId());
    }

}
