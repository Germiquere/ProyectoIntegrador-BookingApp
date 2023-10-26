package com.dh.bmn.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriaBicicletaRequestDto {

    private Long categoriaId;

    @NotNull(message = "El nombre no puede ser nulo" )
    @NotBlank(message = "El nombre no puede estar vacio" )
    private String nombre;

    @NotNull(message = "La descripcion no puede ser nula" )
    @NotBlank(message = "La descripcion no puede estar vacia" )
    private String descripcion;

    @NotNull(message = "La imagen no puede ser nula" )
    @NotBlank(message = "La imagen no puede estar vacia" )
    private String imagen;

}