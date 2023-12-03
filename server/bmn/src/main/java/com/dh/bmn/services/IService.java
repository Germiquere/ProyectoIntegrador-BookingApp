package com.dh.bmn.services;

import com.dh.bmn.pagging.PaginatedResponse;

import java.util.List;
public interface IService <T, E>{

    public void actualizar(E e);

    public T buscarPorId(Long id);

    public void crear(E e);

    public void borrarPorId(Long id);

    public List<T> listarTodos();

    //TODO ---> Analizar si se elimina de aca y su implementacion en el resto de los service y se coloca directamente en el service de bicicleta unicamente
    public PaginatedResponse<T> obtenerPaginacion(int numeroPagina, int limit, int offset);

    //public T buscarPorToken(String token); //AGREGADO
}

