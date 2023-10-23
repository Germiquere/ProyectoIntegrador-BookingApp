package com.dh.bmn.dto;

import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.security.Rol;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UsuarioDto {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Rol rol;
    private List<Bicicleta> bicicletas;

    public UsuarioDto(String nombre, String apellido, String email, String password, Rol rol, List<Bicicleta> bicicletas) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.bicicletas = bicicletas;
    }

}
