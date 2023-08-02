package com.nelumbo.api.repository;

import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParqueaderoRepository extends JpaRepository<Parqueadero, Long> {
    Optional<Parqueadero> findByNombre(String registro);
    List<Parqueadero> findAllBySocio(Usuario usuario);
    Optional<Parqueadero> findBySocioAndId(Usuario usuario, Long id);

}
