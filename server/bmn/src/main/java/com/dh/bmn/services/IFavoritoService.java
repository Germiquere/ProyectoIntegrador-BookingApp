package com.dh.bmn.services;

import com.dh.bmn.dtos.requests.FavoritoRequestDto;
import com.dh.bmn.dtos.responses.FavoritoResponseDto;

import java.util.List;

public interface IFavoritoService {

    void agregarFavorito(FavoritoRequestDto favoritoRequestDto);

    void removerFavorito(Long idFavorito);

    public FavoritoResponseDto buscarFavoritoPorId(Long id);

    public List<FavoritoResponseDto> listarTodosFavoritosPorUsuario(Long idUsuario);
    public List<FavoritoResponseDto> listarTodosFavoritosPorBicicleta(Long idBicicleta);
}
