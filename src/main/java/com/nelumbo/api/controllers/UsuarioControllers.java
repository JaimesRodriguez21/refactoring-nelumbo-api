package com.nelumbo.api.controllers;

import com.nelumbo.api.config.SecurityUtils;
import com.nelumbo.api.dto.request.UsuarioDTO;
import com.nelumbo.api.dto.response.CreatedResponse;
import com.nelumbo.api.exception.AccessDeniedException;
import com.nelumbo.api.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioControllers {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse registrarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        if (SecurityUtils.obtenerRolUsuarioActual().equals("ADMIN")) {
            usuarioService.crearUsuario(usuarioDTO, true);
        } else if (SecurityUtils.obtenerRolUsuarioActual().equals("SOCIO")) {
            usuarioService.crearUsuario(usuarioDTO, false);
        } else {
            throw new AccessDeniedException("acceso denegado");
        }
        return new CreatedResponse(usuarioDTO.getId());
    }

    @GetMapping("/lista")
    public ResponseEntity<List<UsuarioDTO>> ListadoUsuarios() {
        return new ResponseEntity<>(usuarioService.listUsuario(), HttpStatus.OK);
    }

    /*
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@NotNull @PathVariable(name = "id") long id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }
*/

}
