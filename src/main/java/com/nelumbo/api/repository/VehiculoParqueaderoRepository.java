package com.nelumbo.api.repository;

import com.nelumbo.api.entity.VehiculoParqueadero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehiculoParqueaderoRepository extends JpaRepository<VehiculoParqueadero, Long> {
    VehiculoParqueadero findByVehiculo(Long Long);
}
