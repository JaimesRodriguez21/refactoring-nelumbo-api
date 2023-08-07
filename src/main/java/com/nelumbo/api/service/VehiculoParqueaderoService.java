package com.nelumbo.api.service;

import com.nelumbo.api.dto.VehiculoDTO;
import com.nelumbo.api.dto.VehiculoParqueaderoDTO;
import com.nelumbo.api.dto.response.GananciaResponse;
import com.nelumbo.api.dto.response.VehiculoParqueaderoResponse;
import com.nelumbo.api.dto.response.VehiculoResponse;
import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.entity.Vehiculo;
import com.nelumbo.api.entity.VehiculoParqueadero;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VehiculoParqueaderoService {

    void ingresoVehiculo(VehiculoParqueaderoDTO vehiculoParqueaderoDTO);

    void salidaVehiculo(VehiculoDTO vehiculoDTO );

    boolean isIngresoVehiculoParqueadero(Vehiculo vehiculo);

    int vehiculosEnParqueadero(Parqueadero parqueadero);

    List<VehiculoDTO> listVehiculosPorParqueadero(Parqueadero parqueadero);

    List<VehiculoDTO> findVehiculoPorCoincidencia(String indicio);

    Page<VehiculoResponse> findTopVehiculosRegistrados(int pageNumber, int pageSize);
    Page<VehiculoResponse> findTopVehiculosRegistradoPorParqueadero(Parqueadero parqueadero,int pageNumber, int pageSize);

    List<VehiculoParqueaderoResponse> vehiculoPorPrimeraVez();

    GananciaResponse gananciaParqueadero(String tiempo, Long idParqueadero);

    VehiculoParqueadero findByVehiculo(Vehiculo vehiculo);
}
