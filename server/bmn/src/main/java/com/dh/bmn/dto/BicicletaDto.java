package com.dh.bmn.dto;

import com.dh.bmn.embeddable.Imagen;

import java.util.ArrayList;
import java.util.List;

public class BicicletaDto {
    private Integer bicicleta_id;
    private String nombre;
    private String descripcion;
    private List<Imagen> imagenes = new ArrayList<Imagen>();

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

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }
}

