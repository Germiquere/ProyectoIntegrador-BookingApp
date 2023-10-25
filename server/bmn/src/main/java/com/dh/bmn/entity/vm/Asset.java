package com.dh.bmn.entity.vm;

import lombok.Builder;
import lombok.Value;
import java.net.URL;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Asset {
    private byte[] content;
    private String contentType;
}

