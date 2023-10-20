package com.dh.bmn.dto;

public class CategoriaBicicletaDto {
    private Integer categoriaId;
    private String nombre;
    private String descripcion;
    private String imagen;

    public CategoriaBicicletaDto(Integer categoriaId, String nombre, String descripcion, String imagen) {
        this.categoriaId = categoriaId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public CategoriaBicicletaDto() {
    }

    @Override
    public String toString() {
        return "CategoriaBicicleta{" +
                "id=" + categoriaId +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
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
