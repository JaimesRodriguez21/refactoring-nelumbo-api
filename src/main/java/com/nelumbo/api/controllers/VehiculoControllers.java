package com.nelumbo.api.controllers;
import com.nelumbo.api.config.SecurityUtils;
import com.nelumbo.api.dto.request.VehiculoDTO;
;
import com.nelumbo.api.dto.response.CreatedResponse;
import com.nelumbo.api.dto.response.UpdateResponse;
import com.nelumbo.api.exception.AccessDeniedException;
import com.nelumbo.api.service.VehiculoService;
import com.nelumbo.api.exception.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/vehiculos")
public class VehiculoControllers {

    @Autowired
    private VehiculoService vehiculoService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse registrarVehiculo(@Valid @RequestBody VehiculoDTO  vehiculoDTO) {
       if(SecurityUtils.obtenerRolUsuarioActual().equals("CLIENTE")){
            vehiculoService.insertarVehiculo(vehiculoDTO);
       }else{
           throw new AccessDeniedException("Acceso degenado");
       }
       return new CreatedResponse(vehiculoDTO.getId());
    }

    @PutMapping(value = "{idVehiculo}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UpdateResponse actualizarVehiculo(
            @PathVariable(name = "idVehiculo") Long idVehiculo,
            @Valid @RequestBody VehiculoDTO vehiculoDTO
    ){
        if(SecurityUtils.obtenerRolUsuarioActual().equals("CLIENTE")){
            vehiculoService.actualizarVehiculo(idVehiculo,vehiculoDTO);
        }else{
            throw new AccessDeniedException("Acceso degenado");
        }
        return new UpdateResponse(idVehiculo, "actualizado exitosamente");
    }

}
