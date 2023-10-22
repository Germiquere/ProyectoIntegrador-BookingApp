package com.dh.bmn.dtos.responses;

import com.dh.bmn.security.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UsuarioResponseDto {

    private Long usuarioId;
    private String nombre;
    private String apellido;
    private String email;
    private Rol rol;

}
