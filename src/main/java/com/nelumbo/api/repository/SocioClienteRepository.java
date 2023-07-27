package com.nelumbo.api.repository;

import com.nelumbo.api.entity.SocioCliente;
import com.nelumbo.api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface SocioClienteRepository extends JpaRepository<SocioCliente, Long> {
    @Query("SELECT sc FROM SocioCliente sc WHERE sc.socio.id = :socioId AND sc.cliente.id = :clienteId")
    Optional<SocioCliente> findBySocioIdAndClienteId(Long socioId, Long clienteId);
}
