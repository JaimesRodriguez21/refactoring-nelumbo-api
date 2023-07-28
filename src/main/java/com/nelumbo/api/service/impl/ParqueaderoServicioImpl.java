package com.nelumbo.api.service.impl;

import com.nelumbo.api.dto.request.ParqueaderoDTO;
import com.nelumbo.api.dto.request.ParqueaderoSocio;
import com.nelumbo.api.dto.request.UsuarioDTO;
import com.nelumbo.api.dto.request.VehiculoDTO;
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


    @Override
    public List<ParqueaderoDTO> listaParqueaderoDtos() {
        return null;
    }

    @Override
    public void eliminarParqueaderoPorId(Long id) {
        Parqueadero parqueadero = buscarParqueaderoPorId(id);
        parqueaderoRepository.delete(parqueadero);
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
        }
    }

    @Override
    public Parqueadero buscarParqueaderoPorId(Long id) {
        return parqueaderoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El Parqueadero especificado no existe"));
    }


    @Override
    public int saberCantidadVehiculosIngresado(Long idParqueadero) {
        return 0;
    }

    @Override
    public List<VehiculoDTO> obtenerVehiculosEnParqueadero(Long idParqueadero) {
        return null;
    }

    @Override
    public ParqueaderoDTO obtenerParqueaderoSocio(Long idSocio) {
        return null;
    }

    @Override
    public ParqueaderoDTO registrarEntradaVehiculo(Long parqueaderoId, Long idVehiculo) {
        return null;
    }

    @Override
    public ParqueaderoDTO registrarSalidaVehiculo(Long parqueaderoId, Long idVehiculo) {
        return null;
    }

    @Override
    public List<VehiculoDTO> listadoVehiculosParqueaderos() {
        return null;
    }
 /*   @Autowired
    ParqueaderoConverter parqueaderoConverter;

    @Autowired
    ParqueaderoRepository parqueaderoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;


    @Autowired
    VehiculoRepository vehiculoRepository;

    @Autowired
    VehiculoConverter vehiculoConverter;

    @Override
    public ParqueaderoDTO insertarParqueadero(ParqueaderoDTO parqueaderoDTO) {

        boolean isParqueadero = parqueaderoRepository.findByRegistro(parqueaderoDTO.getRegistro()).isEmpty();

        if (isParqueadero) {
            parqueaderoDTO.setEstado(true);
            Parqueadero parqueadero = parqueaderoConverter.parqueaderoDtoToParqueadero(parqueaderoDTO);
            Parqueadero nuevoParqueadero = parqueaderoRepository.save(parqueadero);
            ParqueaderoDTO nuevoParqueaderoDTO = parqueaderoConverter.parqueaderoToParqueaderoDTO(nuevoParqueadero);
            return nuevoParqueaderoDTO;
        } else {
            String mensaje = "El parqueadero con registro " + parqueaderoDTO.getRegistro() + " ya existe";
            throw new IllegalArgumentException(mensaje);
        }

    }

    @Override
    public ParqueaderoDTO buscarParqueaderoPorId(Long id) {
        Parqueadero parqueadero = parqueaderoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El Parqueadero especificado no existe"));

        if (parqueadero.getEstado()) {
            ParqueaderoDTO parqueaderoDTO = parqueaderoConverter.parqueaderoToParqueaderoDTO(parqueadero);
            return parqueaderoDTO;
        } else {
            String mensaje = "El parqueadero no existe";
            throw new IllegalArgumentException(mensaje);
        }

    }

    @Override
    public List<ParqueaderoDTO> listaParqueaderoDtos() {
        List<Parqueadero> listaParqueaderos = parqueaderoRepository.findAll();
        List<ParqueaderoDTO> listaParqueaderosDTO = new ArrayList<>();

        for (Parqueadero parqueadero : listaParqueaderos) {
            if (parqueadero.getEstado()) {
                ParqueaderoDTO parqueaderoDTO = parqueaderoConverter.parqueaderoToParqueaderoDTO(parqueadero);
                listaParqueaderosDTO.add(parqueaderoDTO);
            }
        }
        return listaParqueaderosDTO;
    }

    @Override
    public ParqueaderoDTO eliminarParqueaderoPorId(Long id) {
        Optional<Parqueadero> optionalParqueadero = parqueaderoRepository.findById(id);
        if (optionalParqueadero.isPresent()) {
            Parqueadero parqueadero = optionalParqueadero.get();
            parqueadero.setEstado(false);
            parqueaderoRepository.save(parqueadero);
            return parqueaderoConverter.parqueaderoToParqueaderoDTO(parqueadero);
        } else {
            throw new ResourceNotFoundException("Parqueadero", "id", id);
        }
    }

    @Override
    public ParqueaderoDTO asociarParqueaderoConSocio(Long idUsuario, Long idParqueadero) {
        Optional<Parqueadero> optionalParqueadero = parqueaderoRepository.findById(idParqueadero);
        if (!optionalParqueadero.isPresent()) {
            throw new ResourceNotFoundException("Parqueadero", "id", idParqueadero);
        }

        Parqueadero parqueadero = optionalParqueadero.get();
        if (parqueadero.getSocio() != null) {
            throw new IllegalArgumentException("El Parqueadero ya tiene un socio asignado");
        }

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(idUsuario);
        if (!optionalUsuario.isPresent()) {
            throw new ResourceNotFoundException("Usuario", "id", idUsuario);
        }

        Usuario usuario = optionalUsuario.get();


        parqueadero.setSocio(usuario);
        parqueadero = parqueaderoRepository.save(parqueadero);
        return parqueaderoConverter.parqueaderoToParqueaderoDTO(parqueadero);
    }

    @Override
    public int saberCantidadVehiculosIngresado(Long idParqueadero) {
        Parqueadero parqueadero = parqueaderoRepository.findById(idParqueadero)
                .orElseThrow(() -> new ResourceNotFoundException("Parqueadero", "Id", idParqueadero));
        int vehiculosSalidos = 0;
        for (Vehiculo vehiculo : parqueadero.getVehiculos()) {
            if (vehiculo.getFechaSalida() != null) {
                vehiculosSalidos++;
            }
        }
        return vehiculosSalidos;
    }

    @Override
    public List<VehiculoDTO> obtenerVehiculosEnParqueadero(Long idParqueadero) {
        Parqueadero parqueadero = parqueaderoRepository.findById(idParqueadero)
                .orElseThrow(() -> new ResourceNotFoundException("Parqueadero", "id", idParqueadero));
        List<VehiculoDTO> listVehiculosDtos = new ArrayList<>();
        for (Vehiculo vehiculo : parqueadero.getVehiculos()) {
            if (vehiculo.getFechaSalida() != null) {
                VehiculoDTO vehiculoDTO = vehiculoConverter.vehiculoToVehiculoDto(vehiculo);
                listVehiculosDtos.add(vehiculoDTO);
            }
        }
        return listVehiculosDtos;
    }

    @Override
    public ParqueaderoDTO obtenerParqueaderoSocio(Long idSocio) {
        Usuario usuario = usuarioRepository.findById(idSocio)
                .orElseThrow(() -> new RuntimeException("El Socio especificado no existe"));
        Parqueadero parqueadero = parqueaderoRepository.findBySocio(usuario)
                .orElseThrow(() -> new RuntimeException("El Parqueadero especificado no existe"));

        if (parqueadero.getEstado()) {
            ParqueaderoDTO parqueaderoDTO = parqueaderoConverter.parqueaderoToParqueaderoDTO(parqueadero);
            return parqueaderoDTO;
        } else {
            String mensaje = "El parqueadero no existe";
            throw new IllegalArgumentException(mensaje);
        }
    }

    @Override
    public ParqueaderoDTO registrarEntradaVehiculo(Long parqueaderoId, Long idVehiculo) {
        Optional<Parqueadero> parqueadero = parqueaderoRepository.findById(parqueaderoId);
        if (!parqueadero.isPresent()) {
            throw new RuntimeException("El Parqueadero especificado no existe");
        }

        Optional<Vehiculo> vehiculo = vehiculoRepository.findById(idVehiculo);
        if (!vehiculo.isPresent()) {
            throw new RuntimeException("El vehiculo especificado no existe");
        }

        if (parqueadero.get().getEstado()) {
            vehiculo.get().setParqueadero(parqueadero.get());
            parqueadero.get().getVehiculos().add(vehiculo.get());
            parqueaderoRepository.save(parqueadero.get());
            return parqueaderoConverter.parqueaderoToParqueaderoDTO(parqueadero.get());
        } else {
            String mensaje = "El parqueadero no existe";
            throw new IllegalArgumentException(mensaje);
        }
    }

    @Override
    public ParqueaderoDTO registrarSalidaVehiculo(Long idParqueadero, Long idVehiculo) {
        Optional<Parqueadero> parqueadero = parqueaderoRepository.findById(idParqueadero);
        List<Vehiculo> vehiculos = parqueadero.get().getVehiculos();

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getId().equals(idVehiculo)) {
                vehiculo.setFechaSalida(LocalDateTime.now());
                parqueaderoRepository.save(parqueadero.get());
                return parqueaderoConverter.parqueaderoToParqueaderoDTO(parqueadero.get());
            }
        }

        throw new ResourceNotFoundException("vehiculo", "id", idVehiculo);
    }

    @Override
    public List<VehiculoDTO> listadoVehiculosParqueaderos() {
        List<ParqueaderoDTO> listParqueaderoDTOS = this.listaParqueaderoDtos();
        List<VehiculoDTO> listVehiculos = new ArrayList<>();
        for (ParqueaderoDTO parqueaderoDTO : listParqueaderoDTOS) {
            List<VehiculoDTO> listVehiculosDto = this.obtenerVehiculosEnParqueadero(parqueaderoDTO.getId());
            for (VehiculoDTO vehiculoDTO : listVehiculosDto) {
                listVehiculos.add(vehiculoDTO);
            }

        }
        return listVehiculos;
    }*/


}
