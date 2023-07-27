package com.nelumbo.api.controllers;

import com.nelumbo.api.service.ParqueaderoService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/cliente")
public class ClienteControllers {


    @Autowired
    ParqueaderoService parqueaderoService;

    @GetMapping("/list_detalle_parqueaderos/idparqueadero{idParqueadero}/idsocio{idSocio}")
    public ResponseEntity<?> listaDetalleParqueaderoPorSocio(@PathVariable(name = "idSocio") @NotNull Long idSocio) {
        //String rolUsuarioActual = userDetailService.obtenerRolUsuarioActual();
       /* if ("CLIENTE".equals(rolUsuarioActual)) {
            ParqueaderoDTO parqueaderoDTO = parqueaderoService.obtenerParqueaderoSocio(idSocio);
            List<VehiculoDTO> listVehiculosDto = parqueaderoService.obtenerVehiculosEnParqueadero(parqueaderoDTO.getId());
            return ResponseEntity.ok(listVehiculosDto);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("El usuario no tiene Permisos");
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(errorResponse);
        }*/
        return null;
    }

    @GetMapping("/listado_detalle_parqueadero_socio")
    public ResponseEntity<?> listaDetalleParqueadero() {
/*
        String rolUsuarioActual = userDetailService.obtenerRolUsuarioActual();
        UsuarioDTO usuarioDTO = userDetailService.obtenerUsuarioLogueado();
        if ("CLIENTE".equals(rolUsuarioActual)) {
            List<VehiculoDTO> listVehiculosDTO = parqueaderoService.listadoVehiculosParqueaderos();
            return ResponseEntity.ok(listVehiculosDTO);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("El usuario no tiene Permisos");
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(errorResponse);
        }*/
        return null;
    }

    @PostMapping("/{idParqueadero}/vehiculos/{idVehiculo}/entrada")
    public ResponseEntity<?> registrarEntradaVehiculo(
            @PathVariable Long idParqueadero, @PathVariable Long idVehiculo) {
     /*   String rolUsuarioActual = userDetailService.obtenerRolUsuarioActual();
        UsuarioDTO usuarioDTO = userDetailService.obtenerUsuarioLogueado();
        if ("CLIENTE".equals(rolUsuarioActual)) {
            ParqueaderoDTO parqueaderoDTO = parqueaderoService.registrarEntradaVehiculo(idParqueadero, idVehiculo);
            return ResponseEntity.ok(parqueaderoDTO);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("El usuario no tiene Permisos");
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(errorResponse);
        }*/
        return null;
    }

    @PostMapping("/{idParqueadero}/vehiculos/{idVehiculo}/salida")
    public ResponseEntity<?> registrarSalidaVehiculo(
            @PathVariable Long idParqueadero, @PathVariable Long idVehiculo) {
     /*  String rolUsuarioActual = userDetailService.obtenerRolUsuarioActual();
        UsuarioDTO usuarioDTO = userDetailService.obtenerUsuarioLogueado();
        if ("CLIENTE".equals(rolUsuarioActual)) {
            ParqueaderoDTO parqueaderoDTO = parqueaderoService.registrarSalidaVehiculo(idParqueadero, idVehiculo);
            return ResponseEntity.ok(parqueaderoDTO);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("El usuario no tiene Permisos");
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(errorResponse);
        }*/

        return null;

    }

}
