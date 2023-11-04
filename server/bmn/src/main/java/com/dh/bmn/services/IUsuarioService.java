package com.dh.bmn.services;

public interface IUsuarioService <T> {

    public T buscarPorToken(String token);
}
