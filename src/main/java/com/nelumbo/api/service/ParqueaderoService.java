package com.nelumbo.api.service;

import com.nelumbo.api.dto.request.ParqueaderoDTO;
import com.nelumbo.api.dto.request.ParqueaderoSocio;
import com.nelumbo.api.dto.request.VehiculoDTO;
import com.nelumbo.api.entity.Parqueadero;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParqueaderoService {

    boolean insertarParqueadero(ParqueaderoDTO parqueaderoDTO);

    boolean isEmptyParqueadero(String Registro);

    Parqueadero buscarParqueaderoPorId(Long id);

    boolean asociarParqueaderoConSocio(ParqueaderoSocio parqueaderoSocioRequest);

    void actualizarParqueadero(Long id ,ParqueaderoDTO parqueaderoDTO);

    List<ParqueaderoDTO> listaParqueaderoDtos();

    void eliminarParqueaderoPorId(Long id);

    int saberCantidadVehiculosIngresado(Long idParqueadero);

    List<VehiculoDTO> obtenerVehiculosEnParqueadero(Long idParqueadero);

    ParqueaderoDTO obtenerParqueaderoSocio(Long idSocio);

    ParqueaderoDTO registrarEntradaVehiculo(Long parqueaderoId, Long idVehiculo);

    ParqueaderoDTO registrarSalidaVehiculo(Long parqueaderoId, Long idVehiculo);

    List<VehiculoDTO> listadoVehiculosParqueaderos();


}
