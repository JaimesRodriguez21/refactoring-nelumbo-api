package com.nelumbo.api.service.impl;

import com.nelumbo.api.dto.request.VehiculoDTO;
import com.nelumbo.api.dto.request.VehiculoParqueaderoDTO;
import com.nelumbo.api.entity.*;
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

import java.util.ArrayList;
import java.util.List;

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
    public void ingresoVehiculo(VehiculoParqueaderoDTO vehiculoParqueaderoDTO) {

        Vehiculo vehiculo = vehiculoService.buscarVehiculoPorId(vehiculoParqueaderoDTO.getIdVehiculo());
        if (!isIngresoVehiculoParqueadero(vehiculo)) {
            Parqueadero parqueadero = parqueaderoService.buscarParqueaderoPorId(vehiculoParqueaderoDTO.getIdParqueadero());
            if (!(vehiculosEnParqueadero(parqueadero) < parqueadero.getCantidadVehiculos())) {
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
    @Transactional
    public void salidaVehiculo(VehiculoDTO vehiculoDTO) {
        Vehiculo vehiculo = vehiculoService.buscarVehiculoPorPlaca(vehiculoDTO.getPlaca());
        VehiculoParqueadero vehiculoParqueadero = vehiculoParqueaderoRepository.findByVehiculo(vehiculo);
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
                () -> new NotFoundException("Registro con id: " + id + " no encontrado")
        );
    }

    @Override
    public List<VehiculoDTO> listVehiculos() {
       List<Vehiculo> listVehiculos = vehiculoParqueaderoRepository.findAllVehiculos();
       List<VehiculoDTO> listVehiculoDto = new ArrayList<>();
        for (Vehiculo vehiculo: listVehiculos ) {
            VehiculoDTO vehiculoDTO = VehiculoDTO.
                    builder()
                    .id(vehiculo.getId())
                    .placa(vehiculo.getPlaca())
                    .build();
            listVehiculoDto.add(vehiculoDTO);
        }
       return listVehiculoDto;
    }

    @Override
    public List<VehiculoDTO> listVehiculosPorParqueadero(Long idParqueadero) {
        Parqueadero parqueadero = parqueaderoService.buscarParqueaderoPorId(idParqueadero);
        List<Vehiculo> listVehiculos = vehiculoParqueaderoRepository.findByParqueadero(parqueadero);
        List<VehiculoDTO> listVehiculosDto = new ArrayList<>();
        for (Vehiculo vehiculo : listVehiculos) {
            VehiculoDTO vehiculoDTO = VehiculoDTO
                    .builder()
                    .id(vehiculo.getId())
                    .placa(vehiculo.getPlaca())
                    .build();
            listVehiculosDto.add(vehiculoDTO);
        }
        return listVehiculosDto;

    }
    @Override
    public List<VehiculoDTO> listVehiculosPorSocio(Usuario usuario) {
        Parqueadero parqueadero = parqueaderoService.buscarParqueaderoPorSocio(usuario);
        List<Vehiculo> listVehiculos = vehiculoParqueaderoRepository.findByParqueadero(parqueadero);
        List<VehiculoDTO> listVehiculosDto = new ArrayList<>();
        for (Vehiculo vehiculo : listVehiculos) {
            VehiculoDTO vehiculoDTO = VehiculoDTO
                    .builder()
                    .id(vehiculo.getId())
                    .placa(vehiculo.getPlaca())
                    .build();
            listVehiculosDto.add(vehiculoDTO);
        }
        return listVehiculosDto;
    }
}
