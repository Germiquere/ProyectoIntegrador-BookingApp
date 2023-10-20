package com.dh.bmn.dtos.responses;

import com.dh.bmn.embeddable.Imagen;
import com.dh.bmn.entity.CategoriaBicicleta;
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
    private CategoriaBicicleta categoria;
    private List<Imagen> imagenes;
}
