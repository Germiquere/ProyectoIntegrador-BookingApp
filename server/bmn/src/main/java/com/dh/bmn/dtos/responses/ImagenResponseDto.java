package com.dh.bmn.dtos.responses;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URL;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ImagenResponseDto {

    private String key;
    private URL url;
}