package com.dh.bmn.dtos.requests;

import com.dh.bmn.security.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDto {


    private Long usuarioId;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Rol rol;


}
