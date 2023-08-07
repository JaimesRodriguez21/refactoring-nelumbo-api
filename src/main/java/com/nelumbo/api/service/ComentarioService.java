package com.nelumbo.api.service;

import com.nelumbo.api.dto.ComentarioDTO;
import com.nelumbo.api.entity.Comentario;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComentarioService {

    void insertComentario(ComentarioDTO comentarioDTO);

    void insertRespuestaAComentario(Long idComentario, ComentarioDTO comentarioDTO);

    void updateComentario(long idComentario,ComentarioDTO comentarioDTO);

    void deleteComentario(long idComentario);

    Page<ComentarioDTO> listComentarios(int pageNumber, int pageSize);

    ComentarioDTO searchComentarioPorId(long id);

    Comentario searchComentario(long id);


}
