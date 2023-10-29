package com.dh.bmn.entity;

import com.dh.bmn.config.UrlConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.net.URL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bicicleta_imagenes")
public class Imagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imagen_id")
    private Long id;

    @Column(name = "llave")
    private String key;

    @Convert(converter = UrlConverter.class)
    @Column(name = "url", length = 2048)
    private URL url;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "bicicleta_id")
    private Bicicleta bicicleta;

    @OneToOne()
    @JoinColumn(name = "categoria_id")
    private CategoriaBicicleta categoria;
    public Imagen(String key, URL url) {
        this.key = key;
        this.url = url;
    }
}

