package com.nelumbo.api.service;

import com.nelumbo.api.dto.request.VehiculoDTO;
import com.nelumbo.api.entity.Vehiculo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehiculoService {
    void insertarVehiculo(VehiculoDTO vehiculoDTO);
    void actualizarVehiculo(Long idVehiculo,VehiculoDTO vehiculoDTO);
    boolean existVehiculo(String placa);
    Vehiculo buscarVehiculoPorId(Long id);
    Vehiculo buscarVehiculoPorPlaca(String placa);
    List<VehiculoDTO> listaVehiculoDtos();
    VehiculoDTO asociarVehiculoaParqueadero(Long idParqueadero,Long idVehiculo);

}
