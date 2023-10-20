package com.dh.bmn.embeddable;

import com.dh.bmn.entity.Bicicleta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "imagenes_bicicleta")
public class Imagen {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column (name = "imagen_id")
//    private Long imagenId;

    @Column (length = 500)
    private String url;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "bicicleta_id")
//    private Bicicleta bicicleta;
}
