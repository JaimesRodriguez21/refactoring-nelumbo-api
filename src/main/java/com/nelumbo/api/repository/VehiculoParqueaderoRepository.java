package com.nelumbo.api.repository;

import com.nelumbo.api.entity.Parqueadero;
import com.nelumbo.api.entity.Vehiculo;
import com.nelumbo.api.entity.VehiculoParqueadero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehiculoParqueaderoRepository extends JpaRepository<VehiculoParqueadero, Long> {
    List<VehiculoParqueadero> findByParqueadero(Parqueadero parqueadero);

    int countByParqueadero(Parqueadero parqueadero);

    VehiculoParqueadero findByVehiculo(Vehiculo vehiculo);
}
