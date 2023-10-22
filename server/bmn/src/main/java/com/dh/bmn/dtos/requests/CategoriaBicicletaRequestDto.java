package com.dh.bmn.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriaBicicletaRequestDto {
    private Long categoriaId;
    private String nombre;
    private String descripcion;
    private String imagen;

}
