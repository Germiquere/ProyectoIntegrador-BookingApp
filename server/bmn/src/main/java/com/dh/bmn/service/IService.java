package com.dh.bmn.service;

import com.dh.bmn.dto.BicicletaDto;

import java.util.Optional;
import java.util.Set;

public interface IService <Entity, Dto>{

    public void actualizar(Entity entity) throws Exception;

    public Optional<Dto> buscarPorId(Integer id) throws Exception;

    public void guardar(Entity entity) throws Exception;

    public void borrarPorId(Integer id) throws Exception;

    public Set<BicicletaDto> listarTodos() throws Exception;

}

