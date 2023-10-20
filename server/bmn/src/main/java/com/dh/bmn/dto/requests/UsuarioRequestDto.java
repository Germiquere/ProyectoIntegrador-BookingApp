package com.dh.bmn.dto.requests;

import com.dh.bmn.entity.Reserva;
import com.dh.bmn.security.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioRequestDto {
    private Integer usuarioId;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Rol rol;
    private List<Reserva> reservas;
}
