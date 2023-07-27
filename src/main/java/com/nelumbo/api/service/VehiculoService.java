package com.nelumbo.api.service;

import com.nelumbo.api.dto.request.VehiculoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VehiculoService {
    VehiculoDTO crearVehiculo(VehiculoDTO vehiculoDTO);
    VehiculoDTO buscarVehiculoPorId(Long id);
    List<VehiculoDTO> listaVehiculoDtos();
    VehiculoDTO asociarVehiculoaParqueadero(Long idParqueadero,Long idVehiculo);

}
