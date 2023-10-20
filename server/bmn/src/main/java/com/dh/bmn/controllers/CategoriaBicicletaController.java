package com.dh.bmn.controllers;

import com.dh.bmn.dtos.requests.CategoriaBicicletaRequestDto;
import com.dh.bmn.dtos.responses.CategoriaBicicletaResponseDto;
import com.dh.bmn.services.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bike-me-now/categorias")
public class CategoriaBicicletaController {

    private final IService<CategoriaBicicletaResponseDto, CategoriaBicicletaRequestDto> categoriaBicicletaService;;

    @Autowired
    public CategoriaBicicletaController(IService<CategoriaBicicletaResponseDto, CategoriaBicicletaRequestDto> categoriaBicicletaService) {
        this.categoriaBicicletaService = categoriaBicicletaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaBicicletaResponseDto> obtenerCategoriaPorId (@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(categoriaBicicletaService.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> registrarCategoria (@RequestBody CategoriaBicicletaRequestDto categoriaBicicletaRequestDto) throws Exception {
        categoriaBicicletaService.guardar(categoriaBicicletaRequestDto);
        return new ResponseEntity<>("Nueva categoría de bicicleta registrada.", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoriaPorId (@PathVariable Long id) throws Exception {
        categoriaBicicletaService.borrarPorId(id);
        return new ResponseEntity<>("Categoría de bicicleta eliminada.", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaBicicletaResponseDto>> listaDeCategorias() throws Exception {
        return new ResponseEntity<>(categoriaBicicletaService.listarTodos(), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> actualizarCategoria (@RequestBody CategoriaBicicletaRequestDto categoriaBicicletaRequestDto) throws Exception {
        categoriaBicicletaService.actualizar(categoriaBicicletaRequestDto);
        return new ResponseEntity<>("Categoría de bicicleta actualizada.", HttpStatus.OK);
    }

}
