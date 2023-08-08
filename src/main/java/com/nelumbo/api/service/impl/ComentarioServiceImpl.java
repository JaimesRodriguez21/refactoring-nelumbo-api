package com.nelumbo.api.service.impl;

import com.nelumbo.api.dto.ComentarioDTO;
import com.nelumbo.api.dto.response.ComentarioResponse;
import com.nelumbo.api.entity.Comentario;
import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.exception.BadRequest;
import com.nelumbo.api.repository.ComentarioRepository;
import com.nelumbo.api.service.ComentarioService;
import com.nelumbo.api.service.ParqueaderoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    ComentarioRepository comentarioRepository;

    @Autowired
    ParqueaderoService parqueaderoService;

    @Override
    public void insertComentario(Long idParqueadero, ComentarioDTO comentarioDTO) {
        Parqueadero parqueadero = parqueaderoService.buscarParqueaderoPorId(idParqueadero);
        Comentario comentario = Comentario
                .builder()
                .nombre(comentarioDTO.getNombre())
                .contenido(comentarioDTO.getContenido())
                .parqueadero(parqueadero)
                .build();
        comentarioRepository.save(comentario);
        comentarioDTO.setId(comentario.getId());
    }

    @Override
    public void insertRespuestaAComentario(Long idComentario, ComentarioDTO comentarioDTO) {
        Comentario comentarioPadre = searchComentario(idComentario);
        if (!(comentarioRepository.countByComentarioPadre(comentarioPadre.getId()) < 3)) {
            throw new BadRequest("No se Puede Responder el comentario");
        }
        Comentario comentario = Comentario
                .builder()
                .nombre(comentarioDTO.getNombre())
                .contenido(comentarioDTO.getContenido())
                .comentarioPadre(comentarioPadre)
                .build();
        comentarioRepository.save(comentario);
        comentarioDTO.setId(comentario.getId());
    }

    @Override
    public void updateComentario(long idComentario, ComentarioDTO comentarioDTO) {
        Comentario comentario = searchComentario(idComentario);
        comentario.setContenido(comentarioDTO.getContenido());
        comentario.setNombre(comentarioDTO.getNombre());
        comentarioRepository.save(comentario);
        comentarioDTO.setId(comentario.getId());
    }

    @Override
    public void deleteComentario(long idComentario) {
        Comentario comentario = searchComentario(idComentario);
        deleteComentariosDependientes(comentario);
        comentarioRepository.delete(comentario);
    }

    @Override
    public Page<ComentarioResponse> listComentarios(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Comentario> pageComentarios = comentarioRepository.findAllByComentarioPadreIsNull(pageable);

        List<ComentarioResponse> listComentarioRes = pageComentarios.stream()
                .map(comentarioPadre -> {
                    List<ComentarioDTO> listComentariosDto = comentarioPadre.getRespuestas().stream()
                            .map(comentarioHijo -> ComentarioDTO.builder()
                                    .id(comentarioHijo.getId())
                                    .nombre(comentarioHijo.getNombre())
                                    .contenido(comentarioHijo.getContenido())
                                    .build())
                            .collect(Collectors.toList());

                    ComentarioResponse comentarioResponse = new ComentarioResponse();
                    comentarioResponse.setComentarioPadre(ComentarioDTO.builder()
                            .nombre(comentarioPadre.getNombre())
                            .id(comentarioPadre.getId())
                            .contenido(comentarioPadre.getContenido())
                            .build());
                    comentarioResponse.setComentariosHijos(listComentariosDto);
                    return comentarioResponse;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(listComentarioRes, pageable, pageComentarios.getTotalElements());
    }


    @Override
    public ComentarioResponse searchComentarioPorId(long id) {
        Comentario comentarioPadre = searchComentario(id);
        ComentarioResponse comentarioResponse = new ComentarioResponse();
        List<Comentario> listComentarios = comentarioRepository.findBycomentarioPadre(comentarioPadre);


        comentarioResponse.setComentarioPadre(ComentarioDTO
                .builder()
                .nombre(comentarioPadre.getNombre())
                .id(comentarioPadre.getId())
                .contenido(comentarioPadre.getContenido())
                .build());

        List<ComentarioDTO> listComentariosDto = listComentarios.stream()
                .map(comentario -> ComentarioDTO.builder()
                        .id(comentario.getId())
                        .nombre(comentario.getNombre())
                        .contenido(comentario.getContenido())
                        .build())
                .collect(Collectors.toList());

        comentarioResponse.setComentariosHijos(listComentariosDto);

        return comentarioResponse;
    }

    @Override
    public Comentario searchComentario(long id) {
        return comentarioRepository.findById(id)
                .orElseThrow(() -> new BadRequest("Comentario no encontrado"));
    }

    private void deleteComentariosDependientes(Comentario comentario) {
        List<Comentario> comentariosDependientes = comentarioRepository.findBycomentarioPadre(comentario);
        for (Comentario dependiente : comentariosDependientes) {
            deleteComentariosDependientes(dependiente);
            comentarioRepository.delete(dependiente);
        }
    }

}
