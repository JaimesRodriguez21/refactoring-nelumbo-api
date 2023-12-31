package com.nelumbo.api.service.impl;

import com.nelumbo.api.dto.UsuarioDTO;
import com.nelumbo.api.entity.Rol;
import com.nelumbo.api.entity.Usuario;
import com.nelumbo.api.exception.EmailDuplicadoException;
import com.nelumbo.api.exception.NotFoundException;
import com.nelumbo.api.repository.UsuarioRepository;
import com.nelumbo.api.service.RolService;
import com.nelumbo.api.service.UsuarioService;
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
    public boolean crearUsuario(UsuarioDTO usuarioDTO) {
        Rol rol = rolService.obtenerPorPorId(usuarioDTO.getRolId());
        if(rol.getNombre().equals("ADMIN")){
            throw new NotFoundException("Rol con ID: " + usuarioDTO.getRolId() + " no encontrado.");
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
    public Usuario obtenerUsuarioPorId(long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El usuario con id: " + id + " no Existe"));
    }

    @Override
    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new NotFoundException("Usuario no encontrado")
        );
    }
}
