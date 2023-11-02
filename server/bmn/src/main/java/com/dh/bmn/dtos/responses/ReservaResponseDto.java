package com.dh.bmn.dtos.responses;

import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReservaResponseDto {
    private Usuario usuario;
    private Bicicleta bicicleta;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
