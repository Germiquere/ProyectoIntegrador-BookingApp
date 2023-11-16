package com.dh.bmn.dtos.responses;

import com.dh.bmn.entity.Bicicleta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PoliticaResponseDto {

    private Long politicaId;
    private String titulo;
    private String descripcion;
}
