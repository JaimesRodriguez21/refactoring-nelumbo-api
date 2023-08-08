package com.nelumbo.api.controllers;

import com.nelumbo.api.dto.ComentarioDTO;
import com.nelumbo.api.dto.response.ComentarioResponse;
import com.nelumbo.api.dto.response.CreatedResponse;
import com.nelumbo.api.dto.response.SuccesfullResponse;
import com.nelumbo.api.service.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequestMapping("/comentarios")
public class ComentarioController {

    @Autowired
    ComentarioService comentarioService;


    @PostMapping(value = "/parqueaderos/{idParqueadero}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse insertComentario(
            @PathVariable(name = "idParqueadero") Long idParqueadero,
            @Valid @RequestBody ComentarioDTO comentarioDTO) {
        comentarioService.insertComentario(idParqueadero, comentarioDTO);
        return new CreatedResponse(comentarioDTO.getId());
    }

    @PostMapping(value = "/{idComentario}/respuesta", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedResponse insertRespuestaAComentario(
            @PathVariable(name = "idComentario") Long idComentario,
            @Valid @RequestBody ComentarioDTO comentarioDTO) {
        comentarioService.insertRespuestaAComentario(idComentario, comentarioDTO);
        return new CreatedResponse(comentarioDTO.getId());
    }

    @PutMapping(value = "/{idComentario}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CreatedResponse updateComentario(
            @PathVariable(name = "idComentario") Long idComentario,
            @Valid @RequestBody ComentarioDTO ComentarioDTO) {
        comentarioService.updateComentario(idComentario, ComentarioDTO);
        return new CreatedResponse(ComentarioDTO.getId());
    }

    @DeleteMapping("/{idComentario}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SuccesfullResponse deleteComentario(
            @PathVariable(name = "idComentario") Long idComentario) {
        comentarioService.deleteComentario(idComentario);
        return new SuccesfullResponse("Comentario Eliminado Exitosamente ");
    }


    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Page<ComentarioResponse> getComentarios(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return comentarioService.listComentarios(pageNumber, pageSize);
    }

    @GetMapping(value = "{idComentario}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ComentarioResponse getComentariosPorId(
            @PathVariable(name = "idComentario") Long idComentario) {
        return comentarioService.searchComentarioPorId(idComentario);
    }


}
