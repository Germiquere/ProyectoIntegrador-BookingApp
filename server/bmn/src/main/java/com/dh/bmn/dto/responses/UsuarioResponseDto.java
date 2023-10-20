package com.dh.bmn.dto.responses;

import com.dh.bmn.entity.Reserva;
import com.dh.bmn.security.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UsuarioResponseDto {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Rol rol;
    private List<Reserva> reservas;
}
