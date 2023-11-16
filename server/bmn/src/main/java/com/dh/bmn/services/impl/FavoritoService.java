package com.dh.bmn.services.impl;

import com.dh.bmn.dtos.requests.FavoritoRequestDto;
import com.dh.bmn.dtos.responses.FavoritoResponseDto;
import com.dh.bmn.entity.Favorito;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.IFavoritoRepository;
import com.dh.bmn.services.IFavoritoService;
import com.dh.bmn.util.MapperClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoritoService implements IFavoritoService {

    private final IFavoritoRepository favoritoRepository;

    private final UsuarioService usuarioService;

    private final BicicletaService bicicletaService;

    private static final ObjectMapper objectMapper = MapperClass.objectMapper();

    public FavoritoService(IFavoritoRepository favoritoRepository, UsuarioService usuarioService, BicicletaService bicicletaService) {
        this.favoritoRepository = favoritoRepository;
        this.usuarioService = usuarioService;
        this.bicicletaService = bicicletaService;
    }


    @Override
    public void agregarFavorito(FavoritoRequestDto favoritoRequestDto) {

        usuarioService.buscarPorId(favoritoRequestDto.getUsuario().getUsuarioId());
        bicicletaService.buscarPorId(favoritoRequestDto.getBicicleta().getBicicletaId());

        if (favoritoRepository.findByUsuarioAndBicicleta(favoritoRequestDto.getUsuario().getUsuarioId(), favoritoRequestDto.getBicicleta().getBicicletaId()).isPresent()) {
            throw new ResourceAlreadyExistsException("El favorito ya existe", HttpStatus.CONFLICT.value());
        }

        Favorito favorito = objectMapper.convertValue(favoritoRequestDto, Favorito.class);
        favoritoRepository.save(favorito);

    }

    @Override
    public void removerFavorito(Long idFavorito) {
        Favorito favorito = favoritoRepository.findById(idFavorito).orElseThrow(() -> new ResourceNotFoundException("El favorito no existe", HttpStatus.NOT_FOUND.value()));
        favoritoRepository.delete(favorito);
    }

    @Override
    public FavoritoResponseDto buscarFavoritoPorId(Long id) {
        Favorito favorito = favoritoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El favorito no existe", HttpStatus.NOT_FOUND.value()));
        return objectMapper.convertValue(favorito, FavoritoResponseDto.class);
    }

    @Override
    public List<FavoritoResponseDto> listarTodosFavoritosPorUsuario(Long idUsuario) {

        usuarioService.buscarPorId(idUsuario);

        List<Favorito> listaFavoritos = Optional.of(favoritoRepository.findFavoritosByUsuarioId(idUsuario)).orElseThrow(() -> new ResourceNotFoundException("No se encontraron favoritos para ese usuario", HttpStatus.NOT_FOUND.value()));

        return listaFavoritos
                .stream()
                .map(favorito -> objectMapper.convertValue(favorito, FavoritoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FavoritoResponseDto> listarTodosFavoritosPorBicicleta(Long idBicicleta) {
        bicicletaService.buscarPorId(idBicicleta);

        List<Favorito> listaFavoritos = Optional.of(favoritoRepository.findFavoritosByBicicletaId(idBicicleta)).orElseThrow(() -> new ResourceNotFoundException("No se encontraron favoritos para esa bicicleta", HttpStatus.NOT_FOUND.value()));

        return listaFavoritos
                .stream()
                .map(favorito -> objectMapper.convertValue(favorito, FavoritoResponseDto.class))
                .collect(Collectors.toList());
    }
}
