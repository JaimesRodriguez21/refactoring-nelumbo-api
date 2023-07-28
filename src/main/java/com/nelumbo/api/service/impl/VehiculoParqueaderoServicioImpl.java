package com.nelumbo.api.service.impl;

import com.nelumbo.api.dto.request.VehiculoParqueaderoDTO;
import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.entity.Vehiculo;
import com.nelumbo.api.entity.VehiculoParqueadero;
import com.nelumbo.api.exception.RegistroDuplicadoException;
import com.nelumbo.api.repository.VehiculoParqueaderoRepository;
import com.nelumbo.api.service.ParqueaderoService;
import com.nelumbo.api.service.VehiculoParqueaderoService;
import com.nelumbo.api.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class VehiculoParqueaderoServicioImpl implements VehiculoParqueaderoService {
    @Autowired
    ParqueaderoService parqueaderoService;

    @Autowired
    VehiculoService vehiculoService;

    @Autowired
    VehiculoParqueaderoRepository vehiculoParqueaderoRepository;

    @Override
    public void ingresoVehiculo(VehiculoParqueaderoDTO vehiculoParqueaderoDTO) {
        if(isIngresoVehiculoParqueadero(vehiculoParqueaderoDTO.getIdVehiculo())){
            Parqueadero parqueadero = parqueaderoService.buscarParqueaderoPorId(vehiculoParqueaderoDTO.getIdParqueadero());
            Vehiculo vehiculo = vehiculoService.buscarVehiculoPorId(vehiculoParqueaderoDTO.getIdVehiculo());
            VehiculoParqueadero vehiculoParqueadero = VehiculoParqueadero.builder()
                    .parqueadero(parqueadero)
                    .vehiculo(vehiculo)
                    .build();
            vehiculoParqueaderoRepository.save(vehiculoParqueadero);
            vehiculoParqueaderoDTO.setId(vehiculoParqueaderoDTO.getId());
        }else{
            throw new RegistroDuplicadoException("Vehiculo con id: "
                    + vehiculoParqueaderoDTO.getIdVehiculo() + "se encuentra en parqueadero.");
        }
    }

    @Override
    public void salidaVehiculo(VehiculoParqueaderoDTO vehiculoParqueaderoDTO) {

    }

    @Override
    public boolean isIngresoVehiculoParqueadero(Long id) {
        VehiculoParqueadero vehiculoParqueadero = vehiculoParqueaderoRepository.findByVehiculo(id);
        if (vehiculoParqueadero != null) {
            return true;
        }
        return false;
    }
}
