package com.dh.bmn.service;

import com.dh.bmn.dto.BicicletaDto;
import com.dh.bmn.dto.UsuarioDto;

import java.util.Optional;
import java.util.Set;

public interface IUService <Entity, Dto> {

    public void actualizar(Long id, Dto dto) throws Exception;

    public Optional<Dto> buscarPorId(Long id) throws Exception;

    public void guardar(Dto dto) throws Exception;

    public void borrarPorId(Long id) throws Exception;

    public Set<Dto> listarTodos() throws Exception;
}
