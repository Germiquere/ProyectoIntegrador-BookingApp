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
@Setter
@Getter
public class ReservaRequestDto {
<<<<<<< HEAD
    private Long reservaId;
=======

    private Long reservaId;

>>>>>>> 9c9bbdd5138d4d98b3958af2ff7ccbebc0f7e8f6
    private Usuario usuario;

    private Bicicleta bicicleta;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;
}
