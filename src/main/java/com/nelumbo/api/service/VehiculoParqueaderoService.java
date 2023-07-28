package com.nelumbo.api.service;

import com.nelumbo.api.dto.request.VehiculoParqueaderoDTO;
import com.nelumbo.api.entity.VehiculoParqueadero;

public interface VehiculoParqueaderoService {

    void ingresoVehiculo(VehiculoParqueaderoDTO vehiculoParqueaderoDTO);

    void salidaVehiculo(VehiculoParqueaderoDTO vehiculoParqueaderoDTO);


    boolean isIngresoVehiculoParqueadero(Long id);

}
