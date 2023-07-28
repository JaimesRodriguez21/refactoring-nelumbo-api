package com.nelumbo.api.controllers;


import com.nelumbo.api.config.SecurityUtils;
import com.nelumbo.api.dto.request.ParqueaderoDTO;
import com.nelumbo.api.dto.request.ParqueaderoSocio;
import com.nelumbo.api.dto.response.CreatedResponse;
import com.nelumbo.api.dto.response.DeleteResponse;
import com.nelumbo.api.dto.response.UpdateResponse;
import com.nelumbo.api.exception.AccessDeniedException;
import com.nelumbo.api.service.ParqueaderoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/parqueaderos")
public class ParqueaderoController {
    @Autowired
    private ParqueaderoService parqueaderoService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse registrarParqueadero(@Valid @RequestBody ParqueaderoDTO parqueaderoDTO) {
        if (SecurityUtils.obtenerRolUsuarioActual().equals("ADMIN")) {
            parqueaderoService.insertarParqueadero(parqueaderoDTO);
        } else {
            throw new AccessDeniedException("Acceso denegado");
        }
        return new CreatedResponse(parqueaderoDTO.getId());
    }

    @PutMapping(value = "{idParqueadero}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UpdateResponse actualizarParqueadero(
            @PathVariable(name = "idParqueadero") Long idParqueadero,
            @Valid @RequestBody ParqueaderoDTO parqueaderoDTO) {
        if (SecurityUtils.obtenerRolUsuarioActual().equals("ADMIN")) {
            parqueaderoService.actualizarParqueadero(idParqueadero, parqueaderoDTO);
        } else {
            throw new AccessDeniedException("Acceso denegado");
        }
        return new UpdateResponse(parqueaderoDTO.getId(), "actualizado exitosamente");
    }

    @PostMapping(value = "socio-parqueadero", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse asociarParqueaderoPorSocio(@Valid @RequestBody ParqueaderoSocio parqueaderoSocio) {
        if (SecurityUtils.obtenerRolUsuarioActual().equals("ADMIN")) {
            parqueaderoService.asociarParqueaderoConSocio(parqueaderoSocio);
        } else {
            throw new AccessDeniedException("Acceso denegado");
        }
        return new CreatedResponse(parqueaderoSocio.getId());
    }

    @DeleteMapping(value = "{idParqueadero}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DeleteResponse eliminarParqueaderoPorId(
            @PathVariable(name = "idParqueadero") Long idParqueadero)
    {
        if (SecurityUtils.obtenerRolUsuarioActual().equals("ADMIN")) {
            parqueaderoService.eliminarParqueaderoPorId(idParqueadero);
        } else {
            throw new AccessDeniedException("Acceso denegado");
        }
        return new DeleteResponse("eliminado exitosamente");

    }
}

