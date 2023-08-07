package com.nelumbo.api.controllers;

import com.nelumbo.api.annotations.IngressAllowed;
import com.nelumbo.api.dto.UsuarioDTO;
import com.nelumbo.api.dto.response.CreatedResponse;
import com.nelumbo.api.service.UsuarioService;
import jakarta.validation.Valid;
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

    @IngressAllowed({"ADMIN"})
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse registrarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
    usuarioService.crearUsuario(usuarioDTO);
        return new CreatedResponse(usuarioDTO.getId());
    }

    @GetMapping("/lista")
    public ResponseEntity<List<UsuarioDTO>> ListadoUsuarios() {
        return new ResponseEntity<>(usuarioService.listUsuario(), HttpStatus.OK);
    }

}
