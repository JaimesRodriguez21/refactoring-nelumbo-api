package com.nelumbo.api.service;

import com.nelumbo.api.dto.request.VehiculoParqueaderoDTO;
import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.entity.Vehiculo;
import com.nelumbo.api.entity.VehiculoParqueadero;

public interface VehiculoParqueaderoService {

    void ingresoVehiculo(VehiculoParqueaderoDTO vehiculoParqueaderoDTO);

    void salidaVehiculo(Long id);

    boolean isIngresoVehiculoParqueadero(Vehiculo vehiculo);

    int vehiculosEnParqueadero(Parqueadero parqueadero);

    VehiculoParqueadero buscarVehiculoParqueaderoPorId(long id);

}
