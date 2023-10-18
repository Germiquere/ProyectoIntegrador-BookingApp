/*package com.dh.bmn.entity;

import com.dh.bmn.security.Rol;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    // Otros campos de usuario

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Rol> roles;


}
*/