package com.dh.bmn.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.net.URL;

@Validated
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImagenRequestDto {

    @NotNull(message = "La key no puede ser nula")
    @NotBlank(message = "La key no puede estar vacia")
    private String key;

    @NotNull(message = "La URL no puede ser nula")
    private URL url;
}
