package com.dh.bmn.security.auth;

import com.dh.bmn.security.user.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String nombre;
    private String apellido;
    private String email;
    private String password;

}
