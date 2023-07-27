package com.nelumbo.api.controllers;
import com.nelumbo.api.dto.request.VehiculoDTO;
;
import com.nelumbo.api.service.VehiculoService;
import com.nelumbo.api.exception.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/vehiculo")
public class VehiculoControllers {

    @Autowired
    private VehiculoService vehiculoService;

    @PostMapping
    public ResponseEntity<?> registrarVehiculo(@Valid @RequestBody VehiculoDTO  vehiculoDTO, BindingResult bindingResult) {
        try {
            if (!bindingResult.hasErrors()) {
                return new ResponseEntity<>(vehiculoService.crearVehiculo(vehiculoDTO), HttpStatus.CREATED);
            } else {
                ErrorResponse errorResponse = new ErrorResponse("Campos Incompletos");
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(errorResponse);
            }
        }catch (IllegalArgumentException err){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err.getMessage());

        }catch (Exception err){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err.getMessage());
        }
    }

}
