package com.nelumbo.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Parqueadero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(nullable = false, name = "registro", unique = true)
    private String registro;


    @Column(name = "estado",columnDefinition = "boolean default true")
    private Boolean estado;

    @Column(name = "cantidadvehiculos")
    private Long CantidadVehiculos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="usuario_id")
    private Usuario socio;

    @OneToMany(mappedBy = "parqueadero")
    private List<Vehiculo> vehiculos;;

    @CreationTimestamp
    @Column(name = "fecharegistro")
    private LocalDateTime fechaRegistro;

    @UpdateTimestamp
    @Column(name = "fechaactualizacion")
    private LocalDateTime fechaActualizacion;

}
