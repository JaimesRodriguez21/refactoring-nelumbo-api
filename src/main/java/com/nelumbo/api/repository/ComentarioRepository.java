package com.nelumbo.api.repository;

import com.nelumbo.api.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    @Query(value = "select count(come.comentario_padre_id) from comentario as come "
            + "where come.comentario_padre_id = :idComentario ", nativeQuery = true)
    int countByComentarioPadre(Long idComentario);

    List<Comentario> findBycomentarioPadre(Comentario comentario);
}
