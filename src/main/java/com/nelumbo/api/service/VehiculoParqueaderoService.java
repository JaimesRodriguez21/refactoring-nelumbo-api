package com.nelumbo.api.service;

import com.nelumbo.api.dto.request.VehiculoDTO;
import com.nelumbo.api.dto.request.VehiculoParqueaderoDTO;
import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.entity.Usuario;
import com.nelumbo.api.entity.Vehiculo;
import com.nelumbo.api.entity.VehiculoParqueadero;

import java.util.List;

public interface VehiculoParqueaderoService {

    void ingresoVehiculo(VehiculoParqueaderoDTO vehiculoParqueaderoDTO);

    void salidaVehiculo(VehiculoDTO vehiculoDTO );

    boolean isIngresoVehiculoParqueadero(Vehiculo vehiculo);

    int vehiculosEnParqueadero(Parqueadero parqueadero);

    VehiculoParqueadero buscarVehiculoParqueaderoPorId(long id);

    List<VehiculoDTO> listVehiculos();
    List<VehiculoDTO> listVehiculosPorParqueadero(Long idParqueadero);

    List<VehiculoDTO> listVehiculosPorSocio(Usuario usuario);

}
