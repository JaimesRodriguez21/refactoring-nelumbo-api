package com.nelumbo.api.service.impl;

import com.nelumbo.api.dto.VehiculoDTO;
import com.nelumbo.api.entity.Vehiculo;
import com.nelumbo.api.exception.NotFoundException;
import com.nelumbo.api.repository.VehiculoRepository;
import com.nelumbo.api.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehiculoServicioImpl implements VehiculoService {
    @Autowired
    VehiculoRepository vehiculoRepository;

    @Override
    public void insertarVehiculo(Vehiculo vehiculo) {
        vehiculoRepository.save(vehiculo);
    }

    @Override
    public void actualizarVehiculo(Long idVehiculo, VehiculoDTO vehiculoDTO) {
        Vehiculo vehiculo = buscarVehiculoPorId(idVehiculo);
        if (vehiculo != null) {
            vehiculo.setPlaca(vehiculoDTO.getPlaca());
            vehiculoRepository.save(vehiculo);
            vehiculoDTO.setId(vehiculo.getId());
        }
    }


    @Override
    public Vehiculo buscarVehiculoPorId(Long id) {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El vehiculo con id: " +
                        id + "no se encuentra resgitrado"));
    }

    @Override
    public Vehiculo buscarVehiculoPorPlaca(String placa) {
        return vehiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new NotFoundException("El vehiculo con Placa: " +
                        placa + " no se encuentra resgitrado"));
    }

    @Override
    public boolean existVehiculo(String placa) {
        Optional<Vehiculo> vehiculo = vehiculoRepository.findByPlaca(placa);
        if (vehiculo.isEmpty()) {
            return false;
        }
        return true;
    }
}
