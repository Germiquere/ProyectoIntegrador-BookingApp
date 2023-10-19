package com.dh.bmn.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "categorias_bicicletas")
public class CategoriaBicicleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoria_id;
    @Column
    private String nombre;
    @Column
    private String descripcion;
    @Column (length = 500)
    private String imagen;

    public CategoriaBicicleta(String nombre, String descripcion, String imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public CategoriaBicicleta() {
    }

    @Override
    public String toString() {
        return "CategoriaBicicleta{" +
                "id=" + categoria_id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoriaBicicleta that)) return false;
        return Objects.equals(categoria_id, that.categoria_id) && Objects.equals(nombre, that.nombre) && Objects.equals(descripcion, that.descripcion) && Objects.equals(imagen, that.imagen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoria_id, nombre, descripcion, imagen);
    }

    public Integer getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(Integer categoria_id) {
        this.categoria_id = categoria_id;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
