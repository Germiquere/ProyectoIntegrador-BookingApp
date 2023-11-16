package com.dh.bmn.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FavoritoResponseDto {

    private Long favoritoId;
    private UsuarioResponseDto usuario;
    private BicicletaResponseDto bicicleta;
}
