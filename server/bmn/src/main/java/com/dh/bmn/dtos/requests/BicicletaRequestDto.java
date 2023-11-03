package com.dh.bmn.dtos.requests;

import com.dh.bmn.entity.Imagen;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
import java.util.List;

@Validated
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BicicletaRequestDto {

    private Long bicicletaId;

    @NotNull(message = "El nombre no puede ser nulo" )
    @NotBlank(message = "El nombre no puede estar vacio" )
    private String nombre;

    @NotNull(message = "La descripcion no puede ser nula" )
    @NotBlank(message = "La descripcion no puede estar vacia" )
    private String descripcion;

    @NotNull(message = "El precio no puede ser nulo" )
    @Positive(message = "El precio debe ser mayor a 0")
    private Integer precioAlquilerPorDia;

    private List<CategoriaBicicletaRequestDto> categorias;

    @NotNull(message = "Las imagenes no pueden ser nulas")
    private List<Imagen> imagenes;

    private List <CaracteristicaBicicletaRequestDto> caracteristicas;

}

