package com.nelumbo.api.repository;

import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.entity.Vehiculo;
import com.nelumbo.api.entity.VehiculoParqueadero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehiculoParqueaderoRepository extends JpaRepository<VehiculoParqueadero, Long> {

    int countByParqueadero(Parqueadero parqueadero);

    VehiculoParqueadero findByVehiculo(Vehiculo vehiculo);

    @Query("SELECT vepa.vehiculo FROM VehiculoParqueadero vepa WHERE vepa.parqueadero = :parqueadero")
    List<Vehiculo> findByParqueadero(@Param("parqueadero") Parqueadero parqueadero);

    @Query("SELECT vepa.vehiculo FROM VehiculoParqueadero vepa WHERE vepa.vehiculo.placa LIKE %:placa%")
    List<Vehiculo> findPorCoincidencia(@Param("placa") String placa);


}
