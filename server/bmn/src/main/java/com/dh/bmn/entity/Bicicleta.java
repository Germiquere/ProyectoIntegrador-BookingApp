package com.dh.bmn.entity;

import com.dh.bmn.embeddable.Imagen;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "bicicletas")
public class Bicicleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bicicleta_id")
    private Long bicicletaId;

    @Column
    private String nombre;

    @Column
    private String descripcion;

    @Column(name = "precio_alquiler_por_dia")
    private Integer precioAlquilerPorDia;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaBicicleta categoria;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bicicleta", cascade = CascadeType.ALL)

    @ElementCollection
    @CollectionTable(name = "bicicleta_imagenes", joinColumns = @JoinColumn(name = "bicicleta_id"))
    private List<Imagen> imagenes;


//    @Override
//    public String toString() {
//        return "Bicicleta{" +
//                "id=" + bicicleta_id +
//                ", nombre='" + nombre + '\'' +
//                ", descripcion='" + descripcion + '\'' +
//                ", precioPorDia=" + precioPorDia +
//                ", categoria=" + categoria +
//                ", imagenes=" + imagenes +
//                '}';
//    }

}
