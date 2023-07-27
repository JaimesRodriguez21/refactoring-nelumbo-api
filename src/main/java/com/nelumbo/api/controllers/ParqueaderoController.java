package com.nelumbo.api.controllers;


import com.nelumbo.api.dto.request.ParqueaderoDTO;
import com.nelumbo.api.service.ParqueaderoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parqueadero")
public class ParqueaderoController {
    @Autowired
    private ParqueaderoService parqueaderoService;

    @GetMapping
    public ResponseEntity<List<ParqueaderoDTO>> listaParqueaderoDtos() {
        List<ParqueaderoDTO> listaParqueaderos = parqueaderoService.listaParqueaderoDtos();
        return ResponseEntity.ok(listaParqueaderos);
    }
}

