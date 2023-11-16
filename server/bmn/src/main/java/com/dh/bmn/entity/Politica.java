package com.dh.bmn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "politicas")
public class Politica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "politica_id")
    private Long politicaId;

    @Column
    private String titulo;

    @Column
    private String descripcion;

    @JsonIgnore
    @ManyToMany(mappedBy = "politicas")
    private List<Bicicleta> bicicletas;

    public Politica(Long politicaId, String titulo, String descripcion) {
        this.politicaId = politicaId;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }
}
