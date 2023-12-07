package com.dh.bmn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "valoraciones")
public class Valoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "valoracion_id")
    private Long valoracionId;

    @Column
    private int puntuacion;

    @ManyToOne()
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column
    private String comentario;

    @Column
    private LocalDate fecha;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "bicicleta_id", nullable = false)
    private Bicicleta bicicleta;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

    @PrePersist
    protected void onCreate() {
        fecha = LocalDate.now();
    }
}
