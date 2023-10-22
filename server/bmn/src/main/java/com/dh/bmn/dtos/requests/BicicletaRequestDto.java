package com.dh.bmn.dtos.requests;

import com.dh.bmn.entity.CategoriaBicicleta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BicicletaRequestDto {
    private Long bicicletaId;
    private String nombre;
    private String descripcion;
    private Integer precioAlquilerPorDia;
    private CategoriaBicicleta categoria;


}

