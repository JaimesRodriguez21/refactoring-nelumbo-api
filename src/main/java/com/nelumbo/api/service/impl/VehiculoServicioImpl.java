package com.nelumbo.api.service.impl;

import com.nelumbo.api.dto.request.VehiculoDTO;
import com.nelumbo.api.service.VehiculoService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VehiculoServicioImpl  implements  VehiculoService {
    @Override
    public VehiculoDTO crearVehiculo(VehiculoDTO vehiculoDTO) {
        return null;
    }

    @Override
    public VehiculoDTO buscarVehiculoPorId(Long id) {
        return null;
    }

    @Override
    public List<VehiculoDTO> listaVehiculoDtos() {
        return null;
    }

    @Override
    public VehiculoDTO asociarVehiculoaParqueadero(Long idParqueadero, Long idVehiculo) {
        return null;
    }
/*
    @Autowired
    ParqueaderoRepository parqueaderoRepository;

    @Autowired
    VehiculoRepository vehiculoRepository;

    @Autowired
    ParqueaderoService parqueaderoService;

    @Autowired
    VehiculoConverter vehiculoConverter;

    @Override
    public VehiculoDTO crearVehiculo(VehiculoDTO vehiculoDTO) {
        boolean isVehiculo = vehiculoRepository.findByPlaca(vehiculoDTO.getPlaca()).isEmpty();

        if (isVehiculo) {
            Vehiculo vehiculo = vehiculoConverter.vehiculoDtoToVehiculo(vehiculoDTO);
            Vehiculo nuevoVehiculo = vehiculoRepository.save(vehiculo);
            VehiculoDTO nuevovehiculoDTO = vehiculoConverter.vehiculoToVehiculoDto(nuevoVehiculo);
            return nuevovehiculoDTO;
        } else {
            String mensaje = "El Vehiculo con registro " + vehiculoDTO.getPlaca() + " ya existe";
            throw new IllegalArgumentException(mensaje);
        }
    }

    @Override
    public VehiculoDTO buscarVehiculoPorId(Long id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El Vehiculo especificado no existe"));
        VehiculoDTO vehiculoDTO = vehiculoConverter.vehiculoToVehiculoDto(vehiculo);
        return vehiculoDTO;

    }

    @Override
    public List<VehiculoDTO> listaVehiculoDtos() {
        List<Vehiculo> listaVehiculos = vehiculoRepository.findAll();
        List<VehiculoDTO> listaVehiculoDto = new ArrayList<>();
        for (Vehiculo vehiculo : listaVehiculos) {
            VehiculoDTO vehiculoDTO = vehiculoConverter.vehiculoToVehiculoDto(vehiculo);
            listaVehiculoDto.add(vehiculoDTO);
        }
        return listaVehiculoDto;
    }

    @Override
    public VehiculoDTO asociarVehiculoaParqueadero(Long idParqueadero, Long idVehiculo) {
        Parqueadero parqueadero = parqueaderoRepository.findById(idParqueadero)
                .orElseThrow(() -> new ResourceNotFoundException("Parqueadero", "id", idParqueadero));

        Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo", "id", idVehiculo));

        if (vehiculo.getFechaSalida() != null) {
            throw new IllegalStateException("El vehículo ya ha sido registrado como si hubiera salido.");
        }

        int cantidadVehiculos = parqueaderoService.saberCantidadVehiculosIngresado(idParqueadero);

        if (cantidadVehiculos < parqueadero.getCantidadVehiculos()) {
            vehiculo.setParqueadero(parqueadero);
            vehiculoRepository.save(vehiculo);
            // Actualizar la lista de vehículos en el parqueadero
            parqueadero.getVehiculos().add(vehiculo);
            parqueaderoRepository.save(parqueadero);
            return vehiculoConverter.vehiculoToVehiculoDto(vehiculo);
        } else {
            throw new IllegalArgumentException("El Parqueadero ya esta al limite");
        }
    }

    public VehiculoDTO obtenerDetalleVehiculo(Long idParqueadero, Long idVehiculo) {
        Parqueadero parqueadero = parqueaderoRepository.findById(idParqueadero)
                .orElseThrow(() -> new ResourceNotFoundException("Parqueadero", "id", idParqueadero));

        return vehiculoConverter.vehiculoToVehiculoDto(
                parqueadero.getVehiculos().stream()
                        .filter(vehiculo -> vehiculo.getId().equals(idVehiculo))
                        .findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException("Vehículo", "id", idVehiculo)));
    }
    */

}
