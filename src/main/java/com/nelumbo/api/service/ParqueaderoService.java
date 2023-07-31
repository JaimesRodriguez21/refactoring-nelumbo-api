package com.nelumbo.api.service;

import com.nelumbo.api.dto.request.ParqueaderoDTO;
import com.nelumbo.api.dto.request.ParqueaderoSocio;
import com.nelumbo.api.dto.request.VehiculoDTO;
import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.entity.Usuario;
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

    Parqueadero buscarParqueaderoPorSocio(Usuario socio);
}
