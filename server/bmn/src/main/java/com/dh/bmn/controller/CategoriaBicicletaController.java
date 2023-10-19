package com.dh.bmn.controller;

import com.dh.bmn.dto.requests.CategoriaBicicletaRequestDto;
import com.dh.bmn.entity.CategoriaBicicleta;
import com.dh.bmn.service.impl.CategoriaBicicletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/categorias-bicicletas")
public class CategoriaBicicletaController {

    @Autowired
    private final CategoriaBicicletaService categoriaBicicletaService;

    public CategoriaBicicletaController(CategoriaBicicletaService categoriaBicicletaService) {
        this.categoriaBicicletaService = categoriaBicicletaService;
    }

    @GetMapping("/{id}")
    public Optional<CategoriaBicicletaRequestDto> obtenerCategoriaBicicletaPorId (@PathVariable Integer id) throws Exception {
        return categoriaBicicletaService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> registrarCategoriaBicicleta (@RequestBody CategoriaBicicleta nuevaCategoriaBicicleta) throws Exception {
        categoriaBicicletaService.guardar(nuevaCategoriaBicicleta);
        return ResponseEntity.ok("Nueva categoría de bicicleta registrada.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoriaBicicletaPorId (@PathVariable Integer id) throws Exception {
        categoriaBicicletaService.borrarPorId(id);
        return ResponseEntity.ok("Categoría de bicicleta eliminada.");
    }

    @GetMapping
    public Set<CategoriaBicicletaRequestDto> listaDeCategoriasBicicletas () throws Exception {
        return categoriaBicicletaService.listarTodos();
    }

    @PutMapping()
    public ResponseEntity<?> actualizarCategoriaBicicleta (@RequestBody CategoriaBicicleta categoriaBicicletaActualizada) throws Exception {
        categoriaBicicletaService.actualizar(categoriaBicicletaActualizada);
        return ResponseEntity.ok("Categoría de bicicleta actualizada.");
    }

}
