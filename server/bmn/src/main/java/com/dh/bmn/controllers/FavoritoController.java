package com.dh.bmn.controllers;

import com.dh.bmn.dtos.JsonMessageDto;
import com.dh.bmn.dtos.requests.FavoritoRequestDto;
import com.dh.bmn.dtos.responses.FavoritoResponseDto;
import com.dh.bmn.services.IFavoritoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bike-me-now/api/favoritos")
public class FavoritoController {

    private final IFavoritoService favoritoService;

    public FavoritoController(IFavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }

    @GetMapping("/{id}")
    @Secured({ "USER" })
    public ResponseEntity<FavoritoResponseDto> obtenerFavoritoPorId (@PathVariable Long id) {
        return new ResponseEntity<>(favoritoService.buscarFavoritoPorId(id), HttpStatus.OK);
    }

    @PostMapping
    @Secured("USER")
    public ResponseEntity<?> agregarFavorito (@RequestBody @Valid FavoritoRequestDto favoritoRequestDto){
        favoritoService.agregarFavorito(favoritoRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Nuevo favorito agregado",HttpStatus.CREATED.value()) , HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Secured("USER")
    public ResponseEntity<?> removerFavoritoPorId (@PathVariable Long id){
        favoritoService.removerFavorito(id);
        return new ResponseEntity<>(new JsonMessageDto("Favorito eliminado",HttpStatus.OK.value()), HttpStatus.OK);
    }

    @GetMapping("/usuarios/{id}")
    @Secured("USER")
    public ResponseEntity<List<FavoritoResponseDto>> obtenerFavoritosPorUsuario(@PathVariable Long id) {
        return new ResponseEntity<>(favoritoService.listarTodosFavoritosPorUsuario(id), HttpStatus.OK);
    }

    @GetMapping("/bicicletas/{id}")
    @Secured("USER")
    public ResponseEntity<List<FavoritoResponseDto>> obtenerFavoritosPorBicicleta(@PathVariable Long id) {
        return new ResponseEntity<>(favoritoService.listarTodosFavoritosPorBicicleta(id), HttpStatus.OK);
    }
}
