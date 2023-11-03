package com.dh.bmn.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;


@Validated
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReservaRequestDto {

    private Long reservaId;

    private UsuarioRequestDto usuario;

    private BicicletaRequestDto bicicleta;

    @NotNull(message = "La fecha de inicio no puede ser nula")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin no puede ser nula")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate fechaFin;
}
