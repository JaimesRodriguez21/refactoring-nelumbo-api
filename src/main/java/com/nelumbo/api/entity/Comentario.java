package com.nelumbo.api.entity;

import com.nelumbo.api.dto.ComentarioDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @CreationTimestamp
    @Column(name = "fecharegistro")
    private LocalDateTime fechaRegistro;

    @Column(name = "contenido", nullable = false)
    private String contenido;

    @ManyToOne
    @JoinColumn(name = "parqueadero_id")
    private Parqueadero parqueadero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comentario_padre_id")
    private Comentario comentarioPadre;

    @OneToMany(mappedBy = "comentarioPadre")
    private List<Comentario> respuestas;
}
