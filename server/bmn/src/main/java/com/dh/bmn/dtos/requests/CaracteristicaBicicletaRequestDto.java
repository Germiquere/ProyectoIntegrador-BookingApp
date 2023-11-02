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
public class CaracteristicaBicicletaRequestDto {

    private Long caracteristicaId;

    @NotNull(message = "El nombre no puede ser nulo" )
    @NotBlank(message = "El nombre no puede estar vacio" )
    private String nombre;

    @NotNull(message = "El ícono no puede ser nulo" )
    @NotBlank(message = "El ícono no puede estar vacío" )
    private String icono;
}
