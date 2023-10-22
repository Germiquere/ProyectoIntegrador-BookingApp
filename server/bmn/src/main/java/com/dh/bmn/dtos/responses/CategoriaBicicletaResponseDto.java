package com.dh.bmn.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CategoriaBicicletaResponseDto {

    private String categoriaId;
    private String nombre;
    private String descripcion;
    private String imagen;
}
