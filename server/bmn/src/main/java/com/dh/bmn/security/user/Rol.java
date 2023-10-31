package com.dh.bmn.security.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum Rol {
    @JsonProperty("admin")
    ADMIN("ADMIN"),

    @JsonProperty("user")
    USER("USER");

    private String valor;
    private Rol(String valor) {
        this.valor = valor;
    }
}
