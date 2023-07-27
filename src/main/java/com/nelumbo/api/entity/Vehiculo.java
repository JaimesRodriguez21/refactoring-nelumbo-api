package com.nelumbo.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "placa")
    private String placa;

    @Column(name = "modelo")
    private String modelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parqueadero_id")
    private Parqueadero parqueadero;

    @CreationTimestamp
    @Column(name = "fechaingreso")
    private LocalDateTime fechaIngreso;

    @UpdateTimestamp
    @Column(name = "fechasalida" )
    private LocalDateTime fechaSalida;

    @CreationTimestamp
    @Column(name = "fecharegistro")
    private LocalDateTime fechaRegistro;

    @UpdateTimestamp
    @Column(name = "fechaactualizacion" )
    private LocalDateTime fechaActualizacion;

}
