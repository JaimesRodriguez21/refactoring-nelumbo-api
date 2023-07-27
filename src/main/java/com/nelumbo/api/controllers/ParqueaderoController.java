package com.nelumbo.api.controllers;


import com.nelumbo.api.config.SecurityUtils;
import com.nelumbo.api.dto.request.ParqueaderoDTO;
import com.nelumbo.api.dto.request.ParqueaderoSocioRequest;
import com.nelumbo.api.dto.response.CreatedResponse;
import com.nelumbo.api.exception.AccessDeniedException;
import com.nelumbo.api.service.ParqueaderoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parqueaderos")
public class ParqueaderoController {
    @Autowired
    private ParqueaderoService parqueaderoService;
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse registrarParqueadero(@Valid @RequestBody ParqueaderoDTO parqueaderoDTO){
        if(SecurityUtils.obtenerRolUsuarioActual().equals("ADMIN")){
            parqueaderoService.crearParqueaderoDto(parqueaderoDTO);
        }else{
            throw new AccessDeniedException("Acceso denegado");
        }
        return new CreatedResponse(parqueaderoDTO.getId());
    }


    @PostMapping(value = "/socio-parqueadero", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ParqueaderoDTO>> listaParqueaderoDtos(@Valid @RequestBody ParqueaderoSocioRequest request) {
        List<ParqueaderoDTO> listaParqueaderos = parqueaderoService.listaParqueaderoDtos();
        return ResponseEntity.ok(listaParqueaderos);
    }
}

