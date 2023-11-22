package com.dh.bmn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bicicletas")
public class Bicicleta implements Serializable {

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

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "bicicleta_categoria",
            joinColumns = @JoinColumn(name = "bicicleta_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    @JoinColumn(name = "categoria_id")
    private List<CategoriaBicicleta> categorias;


    @OneToMany(mappedBy = "bicicleta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagen> imagenes;


    @ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "bicicleta_caracteristica",
            joinColumns = @JoinColumn(name = "bicicleta_id"),
            inverseJoinColumns = @JoinColumn(name = "id_caracteristica")
    )

    private List<CaracteristicaBicicleta> caracteristicas;


//    public void addCaracteristica(CaracteristicaBicicleta caracteristica) {
//        if (caracteristicas == null) {
//            caracteristicas = new ArrayList<>();
//        }
//        caracteristicas.add(caracteristica);
//        caracteristica.getBicicletas().add(this);
//    }
//
//    public void removeCaracteristica(CaracteristicaBicicleta caracteristica) {
//        if (caracteristicas != null) {
//            caracteristicas.remove(caracteristica);
//            caracteristica.getBicicletas().remove(this);
//        }
//    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "bicicleta_politica",
            joinColumns = @JoinColumn(name = "bicicleta_id"),
            inverseJoinColumns = @JoinColumn(name = "politica_id")
    )
    private List<Politica> politicas;

    @OneToMany(mappedBy = "bicicleta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reserva> reservas;

}
