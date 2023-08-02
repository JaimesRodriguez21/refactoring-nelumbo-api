package com.nelumbo.api.service.impl;

import com.nelumbo.api.dto.request.VehiculoDTO;
import com.nelumbo.api.dto.request.VehiculoParqueaderoDTO;
import com.nelumbo.api.dto.response.VehiculoResponse;
import com.nelumbo.api.entity.HtVehiculoParqueadero;
import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.entity.Vehiculo;
import com.nelumbo.api.entity.VehiculoParqueadero;
import com.nelumbo.api.exception.BadRequest;
import com.nelumbo.api.exception.RegistroDuplicadoException;
import com.nelumbo.api.repository.HistoricoVehiculoParqueaderoRepository;
import com.nelumbo.api.repository.VehiculoParqueaderoRepository;
import com.nelumbo.api.service.ParqueaderoService;
import com.nelumbo.api.service.VehiculoParqueaderoService;
import com.nelumbo.api.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Transactional
    public void ingresoVehiculo(VehiculoParqueaderoDTO vehiculoParqueaderoDTO) {
        Vehiculo vehiculo;
        if (vehiculoService.existVehiculo(vehiculoParqueaderoDTO.getPlaca())) {
            vehiculo = vehiculoService.buscarVehiculoPorPlaca(vehiculoParqueaderoDTO.getPlaca());
        } else {
            vehiculo = Vehiculo.
                    builder()
                    .placa(vehiculoParqueaderoDTO.getPlaca())
                    .build();

            vehiculoService.insertarVehiculo(vehiculo);
        }

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
            throw new RegistroDuplicadoException("Vehiculo con Placa: "
                    + vehiculoParqueaderoDTO.getPlaca() + " se encuentra en parqueadero.");
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
    public List<VehiculoDTO> listVehiculosPorParqueadero(Parqueadero parqueadero) {
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
    public Page<VehiculoResponse> findTopVehiculosRegistrados(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return historicoVehiculoParqueaderoRepository.findTopVehiculosRegistrados(pageable);

    }

    @Override
    public Page<VehiculoResponse> findTopVehiculosRegistradoPorParqueadero(Parqueadero parqueadero, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return historicoVehiculoParqueaderoRepository.findTopVehiculosRegistradosPorIdParqueadero(parqueadero, pageable);
    }

    @Override
    public List<VehiculoDTO> findVehiculoPorCoincidencia(String indicio) {
        List<Vehiculo> listVehiculos = vehiculoParqueaderoRepository.findPorCoincidencia(indicio);
        List<VehiculoDTO> listVehiculosDTO = new ArrayList<>();
        for (Vehiculo vehiculo : listVehiculos) {
            VehiculoDTO vehiculoDTO = VehiculoDTO.
                    builder()
                    .id(vehiculo.getId())
                    .placa(vehiculo.getPlaca())
                    .build();
            listVehiculosDTO.add(vehiculoDTO);
        }
        return listVehiculosDTO;
    }


}
