package com.nelumbo.api.controllers;


import com.nelumbo.api.annotations.IngressAllowed;
import com.nelumbo.api.dto.request.ParqueaderoDTO;
import com.nelumbo.api.dto.request.ParqueaderoSocio;
import com.nelumbo.api.dto.request.VehiculoDTO;
import com.nelumbo.api.dto.response.CreatedResponse;
import com.nelumbo.api.dto.response.DeleteResponse;
import com.nelumbo.api.dto.response.ParqueaderoResponse;
import com.nelumbo.api.dto.response.UpdateResponse;
import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.service.ParqueaderoService;
import com.nelumbo.api.service.VehiculoParqueaderoService;
import jakarta.validation.Valid;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parqueaderos")
public class ParqueaderoController {
    @Autowired
    private ParqueaderoService parqueaderoService;

    @Autowired
    private VehiculoParqueaderoService vehiculoParqueaderoService;

    @IngressAllowed({"ADMIN"})
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse registrarParqueadero(@Valid @RequestBody ParqueaderoDTO parqueaderoDTO) {
        parqueaderoService.insertarParqueadero(parqueaderoDTO);
        return new CreatedResponse(parqueaderoDTO.getId());
    }

    @IngressAllowed({"ADMIN"})
    @PutMapping(path = "{idParqueadero}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UpdateResponse actualizarParqueadero(
            @PathVariable(name = "idParqueadero") Long idParqueadero,
            @Valid @RequestBody ParqueaderoDTO parqueaderoDTO) {
        parqueaderoService.actualizarParqueadero(idParqueadero, parqueaderoDTO);
        return new UpdateResponse(parqueaderoDTO.getId(), "actualizado exitosamente");
    }

    @IngressAllowed({"ADMIN"})
    @DeleteMapping(path = "{idParqueadero}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DeleteResponse eliminarParqueaderoPorId(
            @PathVariable(name = "idParqueadero") Long idParqueadero) {
        parqueaderoService.eliminarParqueaderoPorId(idParqueadero);
        return new DeleteResponse("eliminado exitosamente");
    }

    @IngressAllowed({"ADMIN"})
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<ParqueaderoDTO> parqueaderos() {
        return parqueaderoService.parqueaderos();
    }

    @IngressAllowed({"ADMIN"})
    @GetMapping(path = "{idParqueadero}")
    @ResponseStatus(HttpStatus.OK)
    public ParqueaderoResponse parqueaderoPorid(
            @PathVariable(value = "idParqueadero") Long idParqueadero
    ) {
        Parqueadero parqueadero = parqueaderoService.buscarParqueaderoPorId(idParqueadero);
        ParqueaderoResponse parqueaderoResponse = ParqueaderoResponse
                .builder()
                .id(parqueadero.getId())
                .nombre(parqueadero.getNombre())
                .costo(parqueadero.getCosto())
                .cantidadVehiculos(parqueadero.getCantidadVehiculos())
                .fechaRegistro(parqueadero.getFechaRegistro())
                .fechaUpdate(parqueadero.getFechaActualizacion())
                .build();
        return parqueaderoResponse;
    }

    @IngressAllowed({"ADMIN"})
    @PostMapping(path = "socios", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse asociarParqueaderoPorSocio(@Valid @RequestBody ParqueaderoSocio parqueaderoSocio) {
        parqueaderoService.asociarParqueaderoConSocio(parqueaderoSocio);
        return new CreatedResponse(parqueaderoSocio.getId());
    }

    @IngressAllowed({"ADMIN"})
    @GetMapping(path = "/{idParqueadero}/vehiculos")
    @ResponseStatus(HttpStatus.OK)
    public List<VehiculoDTO> datelleVehiculos(
            @PathVariable(name = "idParqueadero") Long idParqueadero) {
        Parqueadero parqueadero = parqueaderoService.buscarParqueaderoPorId(idParqueadero);
        return vehiculoParqueaderoService.listVehiculosPorParqueadero(parqueadero);
    }

}

