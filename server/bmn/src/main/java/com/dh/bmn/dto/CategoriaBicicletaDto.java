package com.dh.bmn.dto;

public class CategoriaBicicletaDto {
    private Integer categoria_id;
    private String nombre;
    private String descripcion;
    private String imagen;

    public CategoriaBicicletaDto(Integer categoria_id, String nombre, String descripcion, String imagen) {
        this.categoria_id = categoria_id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public CategoriaBicicletaDto() {
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
