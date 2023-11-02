package com.dh.bmn.services;

import com.dh.bmn.pagging.PaginatedResponse;

import java.util.List;
public interface IService <T, E>{

    public void actualizar(E e);

    public T buscarPorId(Long id);

    public void crear(E e);

    public void borrarPorId(Long id);

    public List<T> listarTodos();

    public PaginatedResponse<T> obtenerPaginacion(int numeroPagina, int limit, int offset);

    public T buscarPorToken(String token); //AGREGADO
}

