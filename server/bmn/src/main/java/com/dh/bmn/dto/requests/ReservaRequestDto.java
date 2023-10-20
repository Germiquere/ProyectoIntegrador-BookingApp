package com.dh.bmn.dto.requests;

import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservaRequestDto {
    private Long reservaId;
    private Usuario usuario;
    private Bicicleta bicicleta;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
