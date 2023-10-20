package com.dh.bmn.entity;


import com.dh.bmn.security.Rol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column (nullable = false)
    private String email;

    @Column (nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    /*@ManyToMany
    @JoinTable(name = "usuario_bicicleta",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "bicicleta_id")
    )
    private List<Bicicleta> bicicletas;*/
    //@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "usuario")
    //private List<Reserva> reservas;

}