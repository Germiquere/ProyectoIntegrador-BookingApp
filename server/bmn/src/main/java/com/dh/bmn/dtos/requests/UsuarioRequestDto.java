package com.dh.bmn.dtos.requests;

import com.dh.bmn.security.user.Rol;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


@Validated
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDto {


    private Long usuarioId;

    @NotNull(message = "El nombre no puede ser nulo" )
    @NotBlank(message = "El nombre no puede estar vacio" )
    @Size(min = 3, max = 10)
    private String nombre;

    @NotNull(message = "El apellido no puede ser nulo" )
    @NotBlank(message = "El apellido no puede estar vacio" )
    @Size(min = 2, max = 10)
    private String apellido;

    @NotNull(message = "El email no puede ser nulo" )
    @NotBlank(message = "El email no puede estar vacio" )
    private String email;

    @NotNull(message = "La contraseña no puede ser nula" )
    @NotBlank(message = "La contraseña no puede estar vacia" )
    @Size(min = 8, max = 12)
    private String password;

    @NotNull(message = "El rol no puede ser nulo")
    private Rol rol;


}
