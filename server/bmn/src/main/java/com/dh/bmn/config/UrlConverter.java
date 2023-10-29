package com.dh.bmn.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.net.URL;

@Converter(autoApply = true)
public class UrlConverter implements AttributeConverter<URL, String> {
    @Override
    public String convertToDatabaseColumn(URL attribute) {
        return attribute != null ? attribute.toString() : null;
    }

    @Override
    public URL convertToEntityAttribute(String dbData) {
        try {
            return dbData != null ? new URL(dbData) : null;
        } catch (Exception e) {
            throw new RuntimeException("Error de conversion a entidad URL");
        }
    }
}
