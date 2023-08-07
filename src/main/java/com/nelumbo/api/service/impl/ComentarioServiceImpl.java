package com.nelumbo.api.service.impl;

import com.nelumbo.api.dto.ComentarioDTO;
import com.nelumbo.api.entity.Comentario;
import com.nelumbo.api.exception.BadRequest;
import com.nelumbo.api.repository.ComentarioRepository;
import com.nelumbo.api.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    ComentarioRepository comentarioRepository;

    @Override
    public void insertComentario(ComentarioDTO comentarioDTO) {
        Comentario comentario = Comentario
                .builder()
                .nombre(comentarioDTO.getNombre())
                .comentario(comentarioDTO.getComentario())
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
                .comentario(comentarioDTO.getComentario())
                .comentarioPadre(comentarioPadre)
                .build();
        comentarioRepository.save(comentario);
        comentarioDTO.setId(comentario.getId());
    }

    @Override
    public void updateComentario(long idComentario, ComentarioDTO comentarioDTO) {
        Comentario comentario = searchComentario(idComentario);
        comentario.setComentario(comentarioDTO.getComentario());
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
    public Page<ComentarioDTO> listComentarios(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Comentario> listComentarios = comentarioRepository.findAll(pageable);
        return listComentarios.map((comentario) ->
                ComentarioDTO.builder()
                        .id(comentario.getId())
                        .nombre(comentario.getNombre())
                        .comentario(comentario.getComentario())
                        .build()
        );

    }

    private ComentarioDTO convertToDto(Comentario comentario) {
        return ComentarioDTO.builder()
                .id(comentario.getId())
                .comentario(comentario.getComentario())
                .nombre(comentario.getNombre())
                .build();
    }

    @Override
    public ComentarioDTO searchComentarioPorId(long id) {
        Comentario comentario = searchComentario(id);
        return ComentarioDTO
                .builder()
                .nombre(comentario.getNombre())
                .id(comentario.getId())
                .comentario(comentario.getComentario())
                .build();
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
