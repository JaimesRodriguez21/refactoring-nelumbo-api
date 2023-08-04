package com.nelumbo.api.controllers;

import com.nelumbo.api.annotations.IngressAllowed;
import com.nelumbo.api.dto.request.VehiculoDTO;
import com.nelumbo.api.dto.response.UpdateResponse;
import com.nelumbo.api.dto.response.VehiculoParqueaderoResponse;
import com.nelumbo.api.dto.response.VehiculoResponse;
import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.service.ParqueaderoService;
import com.nelumbo.api.service.VehiculoParqueaderoService;
import com.nelumbo.api.service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

;

@RestController
@RequestMapping(value = "/vehiculos")
public class VehiculoControllers {

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private VehiculoParqueaderoService vehiculoParqueaderoService;

    @Autowired
    private ParqueaderoService parqueaderoService;

    @IngressAllowed({"CLIENTE"})
    @PutMapping(value = "{idVehiculo}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UpdateResponse actualizarVehiculo(@PathVariable(name = "idVehiculo") Long idVehiculo, @Valid @RequestBody VehiculoDTO vehiculoDTO) {
        vehiculoService.actualizarVehiculo(idVehiculo, vehiculoDTO);
        return new UpdateResponse(idVehiculo, "actualizado exitosamente");
    }

    @IngressAllowed({"ADMIN", "SOCIO"})
    @GetMapping(path = "mas-ingresos", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<VehiculoResponse> vehiculosConMasIngresos(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
        return vehiculoParqueaderoService.findTopVehiculosRegistrados(pageNumber, pageSize);
    }

    @IngressAllowed({"ADMIN", "SOCIO"})
    @GetMapping(path = "mas-ingresos-parqueadero/{idParqueadero}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<VehiculoResponse> vehiculosConMasIngresosPorParqueadero(
            @PathVariable(name = "idParqueadero") Long idParqueadero,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {

        Parqueadero parqueadero = parqueaderoService.buscarParqueaderoPorId(idParqueadero);
        return vehiculoParqueaderoService.findTopVehiculosRegistradoPorParqueadero(parqueadero,pageNumber, pageSize);
    }

    @IngressAllowed({"ADMIN", "SOCIO"})
    @GetMapping(path = "coincidencia", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<VehiculoDTO> buscarVehiculoPorCoincidencia(@RequestParam(defaultValue = "") String textoBusqueda) {
        return vehiculoParqueaderoService.findVehiculoPorCoincidencia(textoBusqueda);
    }


    @IngressAllowed({"ADMIN", "SOCIO"})
    @GetMapping(path = "primera-vez", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<VehiculoParqueaderoResponse> verificarvehiculosPorPrimeraVez() {
        return vehiculoParqueaderoService.vehiculoPorPrimeraVez();
    }


}
