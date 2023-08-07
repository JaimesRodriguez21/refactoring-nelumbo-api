package com.nelumbo.api.service;

import com.nelumbo.api.dto.ParqueaderoDTO;
import com.nelumbo.api.dto.request.ParqueaderoSocio;
import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParqueaderoService {

    boolean insertarParqueadero(ParqueaderoDTO parqueaderoDTO);

    void actualizarParqueadero(Long id ,ParqueaderoDTO parqueaderoDTO);

    void eliminarParqueaderoPorId(Long id);

    List<ParqueaderoDTO> parqueaderos();

    boolean isEmptyParqueadero(String Registro);

    Parqueadero buscarParqueaderoPorId(Long id);

    boolean asociarParqueaderoConSocio(ParqueaderoSocio parqueaderoSocioRequest);

    List<ParqueaderoDTO> parqueaderosPorSocio(Usuario usuario);

    Parqueadero buscarParqueaderoPorSocioAndId(Usuario usuario, Long id);

}
