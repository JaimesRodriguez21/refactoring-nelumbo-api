package com.nelumbo.api.service.impl;

import com.nelumbo.api.dto.request.UsuarioDTO;
import com.nelumbo.api.entity.Rol;
import com.nelumbo.api.entity.Usuario;
import com.nelumbo.api.exception.AccessDeniedException;
import com.nelumbo.api.repository.UsuarioRepository;
import com.nelumbo.api.service.RolService;
import com.nelumbo.api.service.UsuarioService;
import com.nelumbo.api.exception.EmailDuplicadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServicioImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolService rolService;

    @Override
    public boolean crearUsuario(UsuarioDTO usuarioDTO, boolean isAdmin) {
        Rol rol = rolService.obtenerPorPorId(usuarioDTO.getRolId());

        if (!isAdmin) {
            if (rol.getNombre().equals("ADMIN")) {
                throw new AccessDeniedException("Acceso denegado");
            }
        }
        Usuario usuario = Usuario.builder()
                .nombre(usuarioDTO.getNombre())
                .password(passwordEncoder.encode(usuarioDTO.getPass()))
                .email(usuarioDTO.getEmail())
                .rol(rol)
                .build();
        try {
            usuarioRepository.save(usuario);
            usuarioDTO.setId(usuario.getId());
            return true;
        } catch (DataIntegrityViolationException e) {
            throw new EmailDuplicadoException("El usuario con email: " + usuarioDTO.getEmail() + " se encuentra registrado ");
        }
    }

    @Override
    public List<UsuarioDTO> listUsuario() {
        return null;
    }

    @Override
    public UsuarioDTO obtenerUsuarioPorId(long id) {
        return null;
    }

    /*
    @Override
    public List<UsuarioDTO> listUsuario() {
        List<Usuario> listUsuario = usuarioRepository.findAll();
        List<UsuarioDTO> listUsuarioDTO = new ArrayList<>();

        for (Usuario usuario : listUsuario) {
          //  listUsuarioDTO.add(usuarioConverter.usuarioToUsuarioDTO(usuario));
        }
        return listUsuarioDTO;
    }

    @Override
    public UsuarioDTO obtenerUsuarioPorId(long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
        return usuarioConverter.usuarioToUsuarioDTO(usuario);
    }

    /*public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con email " + email + " no existe"));
    }

     */


}
