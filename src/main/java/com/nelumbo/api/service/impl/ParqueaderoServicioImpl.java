package com.nelumbo.api.service.impl;

import com.nelumbo.api.dto.request.ParqueaderoDTO;
import com.nelumbo.api.dto.request.ParqueaderoSocio;
import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.entity.Usuario;
import com.nelumbo.api.exception.BadRequest;
import com.nelumbo.api.exception.NotFoundException;
import com.nelumbo.api.exception.RegistroDuplicadoException;
import com.nelumbo.api.repository.ParqueaderoRepository;
import com.nelumbo.api.service.ParqueaderoService;
import com.nelumbo.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParqueaderoServicioImpl implements ParqueaderoService {

    @Autowired
    ParqueaderoRepository parqueaderoRepository;

    @Autowired
    UsuarioService usuarioService;

    @Override
    public boolean insertarParqueadero(ParqueaderoDTO parqueaderoDTO) {
        if (!this.isEmptyParqueadero(parqueaderoDTO.getNombre())) {
            Parqueadero parqueadero = Parqueadero.builder()
                    .CantidadVehiculos(parqueaderoDTO.getCantidadVehiculos())
                    .nombre(parqueaderoDTO.getNombre())
                    .build();

            parqueaderoRepository.save(parqueadero);
            parqueaderoDTO.setId(parqueadero.getId());
        } else {
            throw new RegistroDuplicadoException("El parqueadero con nombre: "
                    + parqueaderoDTO.getNombre() + " se encuentra registrado");

        }
        return false;
    }

    @Override
    public boolean isEmptyParqueadero(String nombre) {
        Optional<Parqueadero> parqueadero = parqueaderoRepository.findByNombre(nombre);
        if (parqueadero.isEmpty()) {
            return false;
        }
        return true;
    }

    public List<ParqueaderoDTO> parqueaderosPorSocio(Usuario usuario) {
        List<Parqueadero> listParqueadero = parqueaderoRepository.findAllBySocio(usuario);
        List<ParqueaderoDTO> listParqueaderoDto = new ArrayList<>();
        for (Parqueadero parqueadero : listParqueadero) {
            ParqueaderoDTO parqueaderoDTO = ParqueaderoDTO
                    .builder()
                    .id(parqueadero.getId())
                    .nombre(parqueadero.getNombre())
                    .cantidadVehiculos(parqueadero.getCantidadVehiculos())
                    .build();
            listParqueaderoDto.add(parqueaderoDTO);
        }
        return listParqueaderoDto;
    }

    @Override
    public void eliminarParqueaderoPorId(Long id) {
        Parqueadero parqueadero = buscarParqueaderoPorId(id);
        parqueaderoRepository.delete(parqueadero);
    }

    @Override
    public Parqueadero buscarParqueaderoPorSocioAndId(Usuario usuario, Long id) {
        return parqueaderoRepository.findBySocioAndId(usuario, id).orElseThrow(
                ()-> new NotFoundException("Actualmente no se encuentra asociado a ese parqueadero")
        );
    }


    @Override
    public boolean asociarParqueaderoConSocio(ParqueaderoSocio parqueaderoSocio) {
        Parqueadero parqueadero = buscarParqueaderoPorId(parqueaderoSocio.getParqueaderoId());
        Usuario usuario = usuarioService.obtenerUsuarioPorId(parqueaderoSocio.getSocioId());
        if (!usuario.getRol().getNombre().equals("SOCIO")) {
            throw new BadRequest("Usuario con id: " + usuario.getId() + " No es Socio.");
        }

        if (parqueadero.getSocio() == null) {
            parqueadero.setSocio(usuario);
            parqueaderoSocio.setId(parqueadero.getId());
            parqueaderoRepository.save(parqueadero);
            return true;
        } else {
            throw new RegistroDuplicadoException("El parqueadero con nombre: "
                    + parqueadero.getNombre() + " ya se encuentra asociado");
        }
    }

    @Override
    public void actualizarParqueadero(Long id, ParqueaderoDTO parqueaderoDTO) {
        Parqueadero parqueadero = buscarParqueaderoPorId(id);

        if (parqueadero != null) {
            parqueadero.setCantidadVehiculos(parqueaderoDTO.getCantidadVehiculos());
            parqueadero.setNombre(parqueaderoDTO.getNombre());
            parqueaderoRepository.save(parqueadero);
            parqueaderoDTO.setId(parqueadero.getId());
        }
    }

    @Override
    public Parqueadero buscarParqueaderoPorId(Long id) {
        return parqueaderoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El Parqueadero especificado no existe"));
    }

}