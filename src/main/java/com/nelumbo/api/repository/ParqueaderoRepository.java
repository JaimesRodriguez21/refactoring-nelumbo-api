package com.nelumbo.api.repository;

import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParqueaderoRepository extends JpaRepository<Parqueadero, Long> {

    Optional<Parqueadero> findByNombre(String registro);
    Optional<Parqueadero> findBySocio(Usuario usuario);
}
