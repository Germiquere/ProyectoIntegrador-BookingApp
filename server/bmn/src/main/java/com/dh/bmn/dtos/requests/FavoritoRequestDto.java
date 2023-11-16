package com.dh.bmn.dtos.requests;

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
public class FavoritoRequestDto {

    private Long favoritoId;

    private UsuarioRequestDto usuario;

    private BicicletaRequestDto bicicleta;
}
