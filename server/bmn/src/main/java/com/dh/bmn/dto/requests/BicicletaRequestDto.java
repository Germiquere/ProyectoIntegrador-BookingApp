package com.dh.bmn.dto.requests;

import com.dh.bmn.embeddable.Imagen;
import com.dh.bmn.entity.CategoriaBicicleta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

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
    private List<Imagen> imagenes;

}

