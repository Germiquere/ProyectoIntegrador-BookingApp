package com.dh.bmn.service;

import java.util.List;
import java.util.Optional;

public interface IService <T, E>{

    public void actualizar(E e) throws Exception;

    public Optional<T> buscarPorId(Long id) throws Exception;

    public void guardar(E e) throws Exception;

    public void borrarPorId(Long id) throws Exception;

    public List<T> listarTodas() throws Exception;

}

