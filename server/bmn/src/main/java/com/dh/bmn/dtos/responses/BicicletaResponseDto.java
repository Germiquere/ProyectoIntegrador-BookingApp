package com.dh.bmn.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BicicletaResponseDto {

    private String nombre;
    private String descripcion;
    private Integer precioAlquilerPorDia;
    private CategoriaBicicletaResponseDto categoria;

}
