package com.dh.bmn.controllers;

import com.dh.bmn.dtos.JsonMessageDto;
import com.dh.bmn.dtos.requests.CategoriaBicicletaRequestDto;
import com.dh.bmn.dtos.responses.CategoriaBicicletaResponseDto;
import com.dh.bmn.services.IService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/bike-me-now")
public class CategoriaBicicletaController {

    private final IService<CategoriaBicicletaResponseDto, CategoriaBicicletaRequestDto> categoriaBicicletaService;

    @Autowired
    public CategoriaBicicletaController(IService<CategoriaBicicletaResponseDto, CategoriaBicicletaRequestDto> categoriaBicicletaService) {
        this.categoriaBicicletaService = categoriaBicicletaService;
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<CategoriaBicicletaResponseDto> obtenerCategoriaPorId (@PathVariable Long id) {
        return new ResponseEntity<>(categoriaBicicletaService.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping("/api/categorias")
    @Secured("ADMIN")
    public ResponseEntity<?> registrarCategoria (@RequestBody @Valid CategoriaBicicletaRequestDto categoriaBicicletaRequestDto){
        categoriaBicicletaService.crear(categoriaBicicletaRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Nueva categoría de bicicleta registrada.",HttpStatus.CREATED.value()) , HttpStatus.CREATED);
    }

    @DeleteMapping("/api/categorias/{id}")
    @Secured("ADMIN")
    public ResponseEntity<?> eliminarCategoriaPorId (@PathVariable Long id){
        categoriaBicicletaService.borrarPorId(id);
        return new ResponseEntity<>(new JsonMessageDto("Categoría de bicicleta eliminada.",HttpStatus.OK.value()), HttpStatus.OK);
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaBicicletaResponseDto>> listaDeCategorias(){
        return new ResponseEntity<>(categoriaBicicletaService.listarTodos(), HttpStatus.OK);
    }

    @PutMapping("/api/categorias")
    @Secured("ADMIN")
    public ResponseEntity<?> actualizarCategoria (@RequestBody @Valid CategoriaBicicletaRequestDto categoriaBicicletaRequestDto){
        categoriaBicicletaService.actualizar(categoriaBicicletaRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Categoría de bicicleta actualizada.",HttpStatus.OK.value()), HttpStatus.OK);
    }

}
