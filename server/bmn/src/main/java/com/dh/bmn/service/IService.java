package com.dh.bmn.service;

import java.util.List;

public interface IService <T, E>{

    public void actualizar(E e) throws Exception;

    public T buscarPorId(Long id) throws Exception;

    public void guardar(E e) throws Exception;

    public void borrarPorId(Long id) throws Exception;

    public List<T> listarTodas() throws Exception;

}

