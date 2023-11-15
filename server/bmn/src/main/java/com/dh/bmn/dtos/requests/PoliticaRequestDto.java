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
public class PoliticaRequestDto {

    private Long politicaId;

    @NotNull(message = "El titulo no puede ser nulo" )
    @NotBlank(message = "El titulo no puede estar vacio" )
    private String titulo;

    @NotNull(message = "La descripcion no puede ser nula" )
    @NotBlank(message = "La descripcion no puede estar vacia" )
    private String descripcion;

}
