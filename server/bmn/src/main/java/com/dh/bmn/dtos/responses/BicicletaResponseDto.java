package com.dh.bmn.dtos.responses;

import com.dh.bmn.entity.Asset;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BicicletaResponseDto {

    private Long bicicletaId;
    private String nombre;
    private String descripcion;
    private Integer precioAlquilerPorDia;
    private CategoriaBicicletaResponseDto categoria;
    private List<Asset> imagenes;

}
