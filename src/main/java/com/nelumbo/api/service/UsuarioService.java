package com.nelumbo.api.service;

import com.nelumbo.api.dto.request.UsuarioDTO;
import com.nelumbo.api.entity.Rol;

import java.util.List;

public interface UsuarioService {
    boolean crearUsuario(UsuarioDTO usuarioDTO, boolean isAdmin);
    List<UsuarioDTO> listUsuario();
    UsuarioDTO obtenerUsuarioPorId(long id);

}
