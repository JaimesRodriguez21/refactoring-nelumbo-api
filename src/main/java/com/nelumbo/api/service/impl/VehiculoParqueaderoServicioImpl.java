package com.nelumbo.api.service.impl;

import com.nelumbo.api.config.SecurityUtils;
import com.nelumbo.api.dto.VehiculoDTO;
import com.nelumbo.api.dto.VehiculoParqueaderoDTO;
import com.nelumbo.api.dto.response.GananciaResponse;
import com.nelumbo.api.dto.response.VehiculoParqueaderoResponse;
import com.nelumbo.api.dto.response.VehiculoResponse;
import com.nelumbo.api.entity.*;
import com.nelumbo.api.exception.BadRequest;
import com.nelumbo.api.exception.NotFoundException;
import com.nelumbo.api.exception.RegistroDuplicadoException;
import com.nelumbo.api.repository.HistoricoVehiculoParqueaderoRepository;
import com.nelumbo.api.repository.VehiculoParqueaderoRepository;
import com.nelumbo.api.service.ParqueaderoService;
import com.nelumbo.api.service.UsuarioService;
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
    UsuarioService usuarioService;

    @Autowired
    VehiculoParqueaderoRepository vehiculoParqueaderoRepository;

    @Autowired
    HistoricoVehiculoParqueaderoRepository historicoVehiculoParqueaderoRepository;

    @Override
    @Transactional
    public void ingresoVehiculo(VehiculoParqueaderoDTO vehiculoParqueaderoDTO) {
        Vehiculo vehiculo;
        System.out.println("Estoy aca --->");
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
        Usuario socio = usuarioService.obtenerUsuarioPorEmail(SecurityUtils.obtenerUsernameUser());
        VehiculoParqueadero vehiculoParqueadero = findByVehiculo(vehiculo);
        if (vehiculoParqueadero == null) {
            throw new NotFoundException("Vehiculo con placa: " + vehiculo.getPlaca() + " no encontrado");
        }
        if (!vehiculoParqueadero.getParqueadero().getSocio().equals(socio)) {
            throw new BadRequest("El vehiculo se encuentra en un parqueadero no asociado");
        }

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
        VehiculoParqueadero vehiculoParqueadero = findByVehiculo(vehiculo);
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
    public List<VehiculoParqueaderoResponse> vehiculoPorPrimeraVez() {
        List<Object[]> resultado = historicoVehiculoParqueaderoRepository.vehiculoPorPrimeraVez();
        List<VehiculoParqueaderoResponse> listVehiculosParqueadero = new ArrayList<>();
        VehiculoParqueaderoResponse vehiculoParqueaderoResponse = null;
        List<String> listPlacas = null;
        long idParqueadero = 0;

        for (Object[] fila : resultado) {
            Long parqueaderoId = (Long) fila[0];
            String parqueaderoNombre = (String) fila[1];
            Long vehiculoId = (Long) fila[2];
            String vehiculoPlaca = (String) fila[3];

            if (idParqueadero != parqueaderoId) {
                if (vehiculoParqueaderoResponse != null) {
                    vehiculoParqueaderoResponse.setVehiculo(listPlacas);
                    listVehiculosParqueadero.add(vehiculoParqueaderoResponse);
                }
                vehiculoParqueaderoResponse = new VehiculoParqueaderoResponse();
                listPlacas = new ArrayList<>();
                idParqueadero = parqueaderoId;
                vehiculoParqueaderoResponse.setParqueadero(parqueaderoNombre);
            }

            listPlacas.add(vehiculoPlaca);
        }
        if (vehiculoParqueaderoResponse != null) {
            vehiculoParqueaderoResponse.setVehiculo(listPlacas);
            listVehiculosParqueadero.add(vehiculoParqueaderoResponse);
        }
        return listVehiculosParqueadero;
    }

    @Override
    public GananciaResponse gananciaParqueadero(String tiempo, Long idParqueadero) {
        double ganancia = 0.0;
        switch (tiempo) {
            case "dia":
                ganancia = historicoVehiculoParqueaderoRepository.gananciasParqueaderoDia(idParqueadero);
                break;
            case "semana":
                ganancia = historicoVehiculoParqueaderoRepository.gananciasParqueaderoSemana(idParqueadero);
                break;
            case "mes":
                ganancia = historicoVehiculoParqueaderoRepository.gananciasParqueaderoMes(idParqueadero);
                break;
            case "anio":
                ganancia = historicoVehiculoParqueaderoRepository.gananciasParqueaderoAño(idParqueadero);
        }
        GananciaResponse gananciaResponse = GananciaResponse
                .builder()
                .ganancia(ganancia)
                .tiempo(tiempo)
                .build();
        return gananciaResponse;
    }

    @Override
    public VehiculoParqueadero findByVehiculo(Vehiculo vehiculo) {
        return vehiculoParqueaderoRepository.findByVehiculo(vehiculo);
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
