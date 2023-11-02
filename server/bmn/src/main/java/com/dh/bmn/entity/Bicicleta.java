package com.dh.bmn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
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


    @OneToMany(mappedBy = "bicicleta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagen> imagenes;


    @JsonIgnore
    @ManyToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "bicicleta_caracteristica",
            joinColumns = @JoinColumn(name = "bicicleta_id"),
            inverseJoinColumns = @JoinColumn(name = "id_caracteristica")
    )

    private List<CaracteristicaBicicleta> caracteristicas;


    public void addCaracteristica(CaracteristicaBicicleta caracteristica) {
        if (caracteristicas == null) {
            caracteristicas = new ArrayList<>();
        }
        caracteristicas.add(caracteristica);
        caracteristica.getBicicletas().add(this);
    }

    public void removeCaracteristica(CaracteristicaBicicleta caracteristica) {
        if (caracteristicas != null) {
            caracteristicas.remove(caracteristica);
            caracteristica.getBicicletas().remove(this);
        }
    }
}
