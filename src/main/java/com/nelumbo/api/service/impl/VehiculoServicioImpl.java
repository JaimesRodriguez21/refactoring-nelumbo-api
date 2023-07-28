package com.nelumbo.api.service.impl;

import com.nelumbo.api.dto.request.VehiculoDTO;
import com.nelumbo.api.entity.Vehiculo;
import com.nelumbo.api.exception.NotFoundException;
import com.nelumbo.api.exception.RegistroDuplicadoException;
import com.nelumbo.api.repository.VehiculoRepository;
import com.nelumbo.api.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoServicioImpl implements VehiculoService {
    @Autowired
    VehiculoRepository vehiculoRepository;

    @Override
    public void insertarVehiculo(VehiculoDTO vehiculoDTO) {
        if (!existVehiculo(vehiculoDTO.getPlaca())) {
            Vehiculo vehiculo = Vehiculo.builder()
                    .placa(vehiculoDTO.getPlaca())
                    .build();
            vehiculoRepository.save(vehiculo);
            vehiculoDTO.setId(vehiculo.getId());
        } else {
            throw new RegistroDuplicadoException("El Vehiculo con placa " + vehiculoDTO.getPlaca() + " se encuentra registrado ");
        }
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

    @Override
    public List<VehiculoDTO> listaVehiculoDtos() {
        return null;
    }

    @Override
    public VehiculoDTO asociarVehiculoaParqueadero(Long idParqueadero, Long idVehiculo) {
        return null;
    }
}
