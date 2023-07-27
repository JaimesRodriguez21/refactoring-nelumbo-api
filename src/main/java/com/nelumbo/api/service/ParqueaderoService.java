package com.nelumbo.api.service;

import com.nelumbo.api.dto.request.ParqueaderoDTO;
import com.nelumbo.api.dto.request.VehiculoDTO;
import com.nelumbo.api.entity.Parqueadero;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParqueaderoService {

    boolean crearParqueaderoDto(ParqueaderoDTO parqueaderoDTO);

    boolean isEmptyParqueadero(String Registro);


    ParqueaderoDTO buscarParqueaderoPorId(Long id);
    List<ParqueaderoDTO> listaParqueaderoDtos();
    ParqueaderoDTO eliminarParqueaderoPorId(Long id);

    ParqueaderoDTO asociarParqueaderoConSocio(Long idUsuario, Long idParqueadero);

    int saberCantidadVehiculosIngresado(Long idParqueadero);

    List<VehiculoDTO> obtenerVehiculosEnParqueadero(Long idParqueadero);

    ParqueaderoDTO obtenerParqueaderoSocio(Long idSocio);

    ParqueaderoDTO registrarEntradaVehiculo(Long parqueaderoId,Long idVehiculo);

    ParqueaderoDTO registrarSalidaVehiculo(Long parqueaderoId,Long idVehiculo);

    List<VehiculoDTO> listadoVehiculosParqueaderos();


}
