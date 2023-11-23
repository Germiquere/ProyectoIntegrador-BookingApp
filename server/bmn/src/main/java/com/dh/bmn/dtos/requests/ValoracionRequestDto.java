package com.dh.bmn.dtos.requests;

import jakarta.validation.constraints.*;
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
public class ValoracionRequestDto {

    private Long valoracionId;

    private ReservaRequestDto reserva;

    @NotNull(message = "La puntuación no puede ser nula" )
    @Min(1)
    @Max(5)
    private int puntuacion;

    @Size(max = 2000)
    private String comentario;

    private BicicletaRequestDto bicicleta;
}
