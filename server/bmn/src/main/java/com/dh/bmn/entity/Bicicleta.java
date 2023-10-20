package com.dh.bmn.entity;

import com.dh.bmn.embeddable.Imagen;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bicicletas")
public class Bicicleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bicicleta_id")
    private Integer bicicletaId;
    @Column
    private String nombre;
    @Column
    private String descripcion;
    @Column
    private double precioPorDia;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaBicicleta categoria;
    @ElementCollection
    @CollectionTable(name = "bicicleta_imagenes", joinColumns = @JoinColumn(name = "bicicleta_id"))
    private List<Imagen> imagenes = new ArrayList<Imagen>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bicicleta")
    private List<Reserva> reservas;

    @Override
    public String toString() {
        return "Bicicleta{" +
                "id=" + bicicletaId +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioPorDia=" + precioPorDia +
                ", categoria=" + categoria +
                ", imagenes=" + imagenes +
                '}';
    }
}
