package com.nelumbo.api.repository;

import com.nelumbo.api.dto.response.VehiculoResponse;
import com.nelumbo.api.entity.HtVehiculoParqueadero;
import com.nelumbo.api.entity.Parqueadero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;;
import java.util.List;

public interface HistoricoVehiculoParqueaderoRepository extends JpaRepository<HtVehiculoParqueadero, Long> {

    @Query("SELECT new com.nelumbo.api.dto.response.VehiculoResponse(r.vehiculo.placa, COUNT(r)) " +
            "FROM HtVehiculoParqueadero r " +
            "GROUP BY r.vehiculo.placa " +
            "ORDER BY COUNT(r) DESC")
    Page<VehiculoResponse> findTopVehiculosRegistrados(Pageable pageable);

    @Query("SELECT new com.nelumbo.api.dto.response.VehiculoResponse(r.vehiculo.placa, COUNT(r)) " +
            "FROM HtVehiculoParqueadero r " +
            "WHERE r.parqueadero = :parqueadero " +
            "GROUP BY r.vehiculo.placa " +
            "ORDER BY COUNT(r) DESC")
    Page<VehiculoResponse> findTopVehiculosRegistradosPorIdParqueadero(Parqueadero parqueadero, Pageable pageable);

}
