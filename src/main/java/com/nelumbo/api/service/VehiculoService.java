package com.nelumbo.api.service;

import com.nelumbo.api.dto.VehiculoDTO;
import com.nelumbo.api.entity.Vehiculo;
import org.springframework.stereotype.Service;

@Service
public interface VehiculoService {
    void insertarVehiculo(Vehiculo vehiculo);
    void actualizarVehiculo(Long idVehiculo,VehiculoDTO vehiculoDTO);
    boolean existVehiculo(String placa);
    Vehiculo buscarVehiculoPorId(Long id);
    Vehiculo buscarVehiculoPorPlaca(String placa);

}
