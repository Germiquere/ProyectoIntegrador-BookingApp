package com.dh.bmn.dto;

import com.dh.bmn.embeddable.Imagen;
import com.dh.bmn.entity.CategoriaBicicleta;

import java.util.ArrayList;
import java.util.List;

public class BicicletaDto {
    private Integer bicicleta_id;
    private String nombre;
    private String descripcion;
    private double precioPorDia;
    private CategoriaBicicleta categoria;
    private List<Imagen> imagenes = new ArrayList<Imagen>();

    public BicicletaDto(Integer bicicleta_id, String nombre, String descripcion, double precioPorDia, CategoriaBicicleta categoria, List<Imagen> imagenes) {
        this.bicicleta_id = bicicleta_id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioPorDia = precioPorDia;
        this.categoria = categoria;
        this.imagenes = imagenes;
    }

    public BicicletaDto() {
    }

    @Override
    public String toString() {
        return "BicicletaDto{" +
                "id=" + bicicleta_id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioPorDia=" + precioPorDia +
                ", categoria=" + categoria +
                ", imagenes=" + imagenes +
                '}';
    }

    public Integer getBicicleta_id() {
        return bicicleta_id;
    }

    public void setBicicleta_id(Integer bicicleta_id) {
        this.bicicleta_id = bicicleta_id;
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

    public double getPrecioPorDia() {
        return precioPorDia;
    }

    public void setPrecioPorDia(double precioPorDia) {
        this.precioPorDia = precioPorDia;
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

