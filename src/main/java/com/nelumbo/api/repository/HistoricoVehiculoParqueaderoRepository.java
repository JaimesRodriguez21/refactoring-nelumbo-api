package com.nelumbo.api.repository;

import com.nelumbo.api.dto.response.VehiculoResponse;
import com.nelumbo.api.entity.HtVehiculoParqueadero;
import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.entity.VehiculoParqueadero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;;
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

    @Query("SELECT parq.id as parqueaderoId, parq.nombre as parqueaderoNombre, vehi.id as vehiculoId, vehi.placa as vehiculoPlaca " +
            "FROM Parqueadero as parq " +
            "INNER JOIN HtVehiculoParqueadero  as htvp ON htvp.parqueadero.id = parq.id " +
            "INNER JOIN Vehiculo as vehi ON vehi.id = htvp.vehiculo.id " +
            "GROUP BY parq.id, parq.nombre, vehi.id, vehi.placa " +
            "HAVING COUNT(htvp.fechaIngreso) = 1 " +
            "ORDER BY parq.id")
    List<Object[]> vehiculoPorPrimeraVez();

    @Query(value = "SELECT COALESCE(SUM(EXTRACT(EPOCH FROM (htvp.fechasalida - htvp.fechaingreso)) / 60 * parq.costo), 0) AS ganancias_hoy " +
            "FROM ht_vehiculo_parqueadero htvp " +
            "Inner join parqueadero as parq on (htvp.parqueadero_id = parq.id) " +
            "WHERE htvp.parqueadero_id = :idParqueadero " +
            "AND htvp.fechaingreso >= CURRENT_DATE " +
            "AND htvp.fechasalida IS NOT NULL", nativeQuery = true)
    double gananciasParqueaderoDia(Long idParqueadero);

    @Query(value = "SELECT COALESCE(SUM(EXTRACT(EPOCH FROM (htvp.fechasalida - htvp.fechaingreso)) / 60 * parq.costo), 0) AS ganancias_semana " +
            "FROM ht_vehiculo_parqueadero htvp " +
            "Inner join parqueadero as parq on (htvp.parqueadero_id = parq.id) " +
            "WHERE htvp.parqueadero_id = :idParqueadero " +
            "AND htvp.fechaingreso >= (SELECT CURRENT_DATE - INTERVAL '1 day' * (EXTRACT(ISODOW FROM CURRENT_DATE) - 1)) " +
            "AND htvp.fechaingreso <= (SELECT CURRENT_DATE + INTERVAL '1 day' * (7 - EXTRACT(ISODOW FROM CURRENT_DATE))) " +
            "AND htvp.fechasalida IS NOT NULL", nativeQuery = true)
    double gananciasParqueaderoSemana(@Param("idParqueadero") Long idParqueadero);

    @Query(value = "SELECT COALESCE(SUM(EXTRACT(EPOCH FROM (htvp.fechasalida - htvp.fechaingreso)) / 60 * parq.costo), 0) AS ganancias_mes " +
            "FROM ht_vehiculo_parqueadero as htvp " +
            "Inner join parqueadero as parq on (htvp.parqueadero_id = parq.id) " +
            "WHERE htvp.parqueadero_id = :idParqueadero " +
            "AND htvp.fechaingreso >= DATE_TRUNC('month', CURRENT_DATE) " +
            "AND htvp.fechasalida <= DATE_TRUNC('month', CURRENT_DATE) + INTERVAL '1 month'", nativeQuery = true)
    double gananciasParqueaderoMes(@Param("idParqueadero") Long idParqueadero);

    @Query(value = "SELECT COALESCE(SUM(EXTRACT(EPOCH FROM (htvp.fechasalida - htvp.fechaingreso)) / 60 * parq.costo), 0) AS ganancias_anio " +
            "FROM ht_vehiculo_parqueadero as htvp " +
            "Inner join parqueadero as parq on (htvp.parqueadero_id = parq.id) " +
            "WHERE parqueadero_id = :idParqueadero " +
            "AND htvp.fechaingreso >= DATE_TRUNC('year', CURRENT_DATE)" +
            "AND htvp.fechasalida IS NOT NULL", nativeQuery = true)
    double gananciasParqueaderoAño(@Param("idParqueadero") Long idParqueadero);




}
