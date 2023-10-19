package com.dh.bmn.dto.responses;

import com.dh.bmn.embeddable.Imagen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CategoriaBicicletaResponseDto {

    private String nombre;
    private String descripcion;
    private Imagen imagen;
}
