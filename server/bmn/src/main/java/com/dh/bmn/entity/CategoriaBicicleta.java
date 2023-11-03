package com.dh.bmn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "categorias_bicicletas")
public class CategoriaBicicleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long categoriaId;

    @Column
    private String nombre;

    @Column
    private String descripcion;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "imagen_id")
    private Imagen imagen;

    @JsonIgnore
    @ManyToMany(mappedBy = "categorias")
    private List<Bicicleta> bicicletas;

    public CategoriaBicicleta(Long categoriaId, String nombre, String descripcion, Imagen imagen) {
        this.categoriaId = categoriaId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }
}
