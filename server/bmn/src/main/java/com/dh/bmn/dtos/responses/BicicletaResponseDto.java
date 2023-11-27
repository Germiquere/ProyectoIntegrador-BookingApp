package com.dh.bmn.dtos.responses;

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
    private List<CategoriaBicicletaResponseDto> categorias;
    private List<ImagenResponseDto> imagenes;
    private List<CaracteristicaBicicletaResponseDto> caracteristicas;
    private List<PoliticaResponseDto> politicas;
    private List<ValoracionResponseDto> valoraciones;
    private Double promedioPuntuacion;
    private Long cantidadValoraciones;

}
