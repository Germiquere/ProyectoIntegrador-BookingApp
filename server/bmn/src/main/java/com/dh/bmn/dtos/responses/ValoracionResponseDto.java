package com.dh.bmn.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ValoracionResponseDto {
        private Long valoracionId;
        private UsuarioResponseDto usuario;
        private BicicletaResponseDto bicicleta;
        private int puntuacion;
        private String comentario;
        private LocalDate fecha;
}
