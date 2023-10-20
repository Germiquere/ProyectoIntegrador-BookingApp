package com.dh.bmn.entity;

import com.dh.bmn.embeddable.Imagen;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
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

//    @OneToOne(cascade = CascadeType.ALL)
////    @Column(length = 500)
//    @JoinColumn(name = "imagen_id", referencedColumnName = "imagen_id")
    private String imagen;

}
