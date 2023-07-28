package com.nelumbo.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class HtVehiculoParqueadero {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "parqueadero_id")
    private Parqueadero parqueadero;

    @Column(name = "fechaingreso")
    private LocalDateTime fechaIngreso;

    @CreationTimestamp
    @Column(name = "fechasalida")
    private LocalDateTime fechaSalida;

}
