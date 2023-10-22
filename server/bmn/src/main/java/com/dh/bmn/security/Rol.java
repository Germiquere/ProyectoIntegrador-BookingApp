package com.dh.bmn.security;

import lombok.Getter;

@Getter
public enum Rol {
    ADMIN("admin"),
    USER("user");

    private String valor;
    private Rol(String valor) {
        this.valor = valor;
    }
}
