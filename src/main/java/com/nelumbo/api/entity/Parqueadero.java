package com.nelumbo.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Parqueadero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "nombre", unique = true)
    private String nombre;

    @Column(name = "cantidadvehiculos")
    private Long CantidadVehiculos;

    @Column(name = "costo")
    private Double costo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="usuario_id")
    private Usuario socio;

    @CreationTimestamp
    @Column(name = "fecharegistro")
    private LocalDateTime fechaRegistro;

    @UpdateTimestamp
    @Column(name = "fechaactualizacion")
    private LocalDateTime fechaActualizacion;
}
