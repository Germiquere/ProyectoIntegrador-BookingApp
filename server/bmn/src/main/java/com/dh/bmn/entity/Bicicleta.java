package com.dh.bmn.entity;

import com.dh.bmn.embeddable.Imagen;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bicicletas")
public class Bicicleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bicicleta_id;
    @Column
    private String nombre;
    @Column
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaBicicleta categoria;
    @ElementCollection
    @CollectionTable(name = "bicicleta_imagenes", joinColumns = @JoinColumn(name = "bicicleta_id"))
    private List<Imagen> imagenes = new ArrayList<Imagen>();

    public Bicicleta(String nombre, String descripcion, CategoriaBicicleta categoria, List<Imagen> imagenes) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.imagenes = imagenes;
    }

    public Bicicleta() {}

    @Override
    public String toString() {
        return "Bicicleta{" +
                "id=" + bicicleta_id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagen='" + imagenes + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bicicleta bicicleta)) return false;
        return bicicleta_id.equals(bicicleta.bicicleta_id) && nombre.equals(bicicleta.nombre) && descripcion.equals(bicicleta.descripcion) && categoria.equals(bicicleta.categoria) && imagenes.equals(bicicleta.imagenes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bicicleta_id, nombre, descripcion, categoria, imagenes);
    }

    public Integer getBicicleta_id() {
        return bicicleta_id;
    }

    public void setBicicleta_id(Integer producto_id) {
        this.bicicleta_id = producto_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CategoriaBicicleta getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaBicicleta categoria) {
        this.categoria = categoria;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }
}
