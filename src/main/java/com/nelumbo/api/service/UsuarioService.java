package com.nelumbo.api.service;

import com.nelumbo.api.dto.request.UsuarioDTO;
import com.nelumbo.api.entity.Usuario;

import java.util.List;

public interface UsuarioService {
    boolean crearUsuario(UsuarioDTO usuarioDTO, boolean isAdmin);
    List<UsuarioDTO> listUsuario();
    Usuario obtenerUsuarioPorId(long id);

    Usuario obtenerUsuarioPorEmail(String email);

}
