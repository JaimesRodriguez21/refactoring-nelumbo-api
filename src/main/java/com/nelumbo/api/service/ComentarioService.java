package com.nelumbo.api.service;

import com.nelumbo.api.dto.ComentarioDTO;
import com.nelumbo.api.dto.response.ComentarioResponse;
import com.nelumbo.api.entity.Comentario;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComentarioService {

    void insertComentario(Long idParqueadero, ComentarioDTO comentarioDTO);

    void insertRespuestaAComentario(Long idComentario, ComentarioDTO comentarioDTO);

    void updateComentario(long idComentario,ComentarioDTO comentarioDTO);

    void deleteComentario(long idComentario);

    Page<ComentarioResponse> listComentarios(int pageNumber, int pageSize);

    ComentarioResponse searchComentarioPorId(long id);

    Comentario searchComentario(long id);


}
