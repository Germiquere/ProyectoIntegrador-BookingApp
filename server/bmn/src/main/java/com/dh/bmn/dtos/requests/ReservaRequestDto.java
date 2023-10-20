package com.dh.bmn.dtos.requests;

import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReservaRequestDto {
    private Long reservaId;

    private Usuario usuario;

    private Bicicleta bicicleta;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;
}
