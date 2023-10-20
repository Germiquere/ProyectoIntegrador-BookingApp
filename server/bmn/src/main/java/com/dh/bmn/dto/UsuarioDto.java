package com.dh.bmn.dto;

import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.entity.Reserva;
import com.dh.bmn.security.Rol;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UsuarioDto {

    private Integer usuarioId;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Rol rol;
    //private List<Bicicleta> bicicletas;
    private List<Reserva> reservas;

    public UsuarioDto(String nombre, String apellido, String email, String password, Rol rol, List<Reserva> reservas) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.reservas = reservas;
    }

}
