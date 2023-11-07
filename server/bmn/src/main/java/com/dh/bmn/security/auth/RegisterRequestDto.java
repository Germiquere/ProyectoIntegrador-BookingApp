package com.dh.bmn.security.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

    @NotNull(message = "El nombre no puede ser nulo" )
    @NotBlank(message = "El nombre no puede estar vacio" )
    private String nombre;

    @NotNull(message = "El apellido no puede ser nulo" )
    @NotBlank(message = "El apellido no puede estar vacio" )
    private String apellido;

    @NotNull(message = "El email no puede ser nulo" )
    @NotBlank(message = "El email no puede estar vacio" )
    private String email;

    @NotNull(message = "La contraseña no puede ser nula" )
    @NotBlank(message = "La contraseña no puede estar vacia" )
    @Size(min = 8, max = 12)
    private String password;

}
