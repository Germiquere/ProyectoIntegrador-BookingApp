package com.dh.bmn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id")
<<<<<<< HEAD
    private Long reservaId;
=======
    private Integer reservaId;

>>>>>>> 9c9bbdd5138d4d98b3958af2ff7ccbebc0f7e8f6
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "bicicleta_id")
    private Bicicleta bicicleta;

    @Column
    private LocalDate fechaInicio;

    @Column
    private LocalDate fechaFin;

}
