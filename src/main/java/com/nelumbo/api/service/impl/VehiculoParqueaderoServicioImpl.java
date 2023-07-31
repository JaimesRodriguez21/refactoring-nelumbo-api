package com.nelumbo.api.service.impl;

import com.nelumbo.api.dto.request.VehiculoParqueaderoDTO;
import com.nelumbo.api.entity.HtVehiculoParqueadero;
import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.entity.Vehiculo;
import com.nelumbo.api.entity.VehiculoParqueadero;
import com.nelumbo.api.exception.BadRequest;
import com.nelumbo.api.exception.NotFoundException;
import com.nelumbo.api.exception.RegistroDuplicadoException;
import com.nelumbo.api.repository.HistoricoVehiculoParqueaderoRepository;
import com.nelumbo.api.repository.VehiculoParqueaderoRepository;
import com.nelumbo.api.service.ParqueaderoService;
import com.nelumbo.api.service.VehiculoParqueaderoService;
import com.nelumbo.api.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehiculoParqueaderoServicioImpl implements VehiculoParqueaderoService {
    @Autowired
    ParqueaderoService parqueaderoService;

    @Autowired
    VehiculoService vehiculoService;

    @Autowired
    VehiculoParqueaderoRepository vehiculoParqueaderoRepository;

    @Autowired
    HistoricoVehiculoParqueaderoRepository historicoVehiculoParqueaderoRepository;

    @Override
    @Transactional
    public void ingresoVehiculo(VehiculoParqueaderoDTO vehiculoParqueaderoDTO) {

        Vehiculo vehiculo = vehiculoService.buscarVehiculoPorId(vehiculoParqueaderoDTO.getIdVehiculo());
        if (!isIngresoVehiculoParqueadero(vehiculo)) {
            Parqueadero parqueadero = parqueaderoService.buscarParqueaderoPorId(vehiculoParqueaderoDTO.getIdParqueadero());
            if (!(vehiculosEnParqueadero(parqueadero) <= parqueadero.getCantidadVehiculos())) {
                throw new BadRequest("El parqueadero se encuentra al limite");
            }
            VehiculoParqueadero vehiculoParqueadero = VehiculoParqueadero.builder()
                    .parqueadero(parqueadero)
                    .vehiculo(vehiculo)
                    .build();
            vehiculoParqueaderoRepository.save(vehiculoParqueadero);
            vehiculoParqueaderoDTO.setId(vehiculoParqueadero.getId());
        } else {
            throw new RegistroDuplicadoException("Vehiculo con id: "
                    + vehiculoParqueaderoDTO.getIdVehiculo() + " se encuentra en parqueadero.");
        }
    }

    @Override
    public void salidaVehiculo(Long id) {
        VehiculoParqueadero vehiculoParqueadero = buscarVehiculoParqueaderoPorId(id);
        HtVehiculoParqueadero htVehiculoParqueadero = HtVehiculoParqueadero.builder()
                .vehiculo(vehiculoParqueadero.getVehiculo())
                .parqueadero(vehiculoParqueadero.getParqueadero())
                .fechaIngreso(vehiculoParqueadero.getFechaIngreso())
                .build();
        historicoVehiculoParqueaderoRepository.save(htVehiculoParqueadero);
        vehiculoParqueaderoRepository.delete(vehiculoParqueadero);
    }

    @Override
    public boolean isIngresoVehiculoParqueadero(Vehiculo vehiculo) {
        VehiculoParqueadero vehiculoParqueadero = vehiculoParqueaderoRepository.findByVehiculo(vehiculo);
        if (vehiculoParqueadero != null) {
            return true;
        }
        return false;
    }

    @Override
    public int vehiculosEnParqueadero(Parqueadero parqueadero) {
        return vehiculoParqueaderoRepository.countByParqueadero(parqueadero);
    }

    @Override
    public VehiculoParqueadero buscarVehiculoParqueaderoPorId(long id) {
        return vehiculoParqueaderoRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Registro con id: " + id + "no encontrado")
        );
    }
}
