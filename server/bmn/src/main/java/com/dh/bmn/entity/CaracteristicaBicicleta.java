package com.dh.bmn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "caracteristicas_bicicletas")

public class CaracteristicaBicicleta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caracteristica")
    private Long caracteristicaId;

    @Column
    private String nombre;

    @Column
    private String icono;


    @JsonIgnore
    @ManyToMany(mappedBy = "caracteristicas", cascade = {CascadeType.PERSIST, CascadeType.MERGE})

    private List<Bicicleta> bicicletas;

    public CaracteristicaBicicleta(Long caracteristicaId, String nombre, String icono) {
        this.caracteristicaId = caracteristicaId;
        this.nombre = nombre;
        this.icono = icono;
    }
}
