package com.dh.bmn.controller;

import com.dh.bmn.dto.BicicletaDto;
import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.service.BicicletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bicicletas")
public class BicicletaController {

    @Autowired
    private final BicicletaService bicicletaService;

    public BicicletaController(BicicletaService bicicletaService) {
        this.bicicletaService = bicicletaService;
    }

    @GetMapping("/{id}")
    public Optional<BicicletaDto> obtenerBicicletaPorId (@PathVariable Integer id) throws Exception {
        return bicicletaService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> registrarBicicleta (@RequestBody Bicicleta nuevaBicicleta) throws Exception {
        bicicletaService.guardar(nuevaBicicleta);
        return ResponseEntity.ok("Nueva bicicleta registrada.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarBicicletaPorId (@PathVariable Integer id) throws Exception {
        bicicletaService.borrarPorId(id);
        return ResponseEntity.ok("Bicicleta eliminada.");
    }

    @GetMapping
    public Set<BicicletaDto> listaDeBicicletas () throws Exception {
        return bicicletaService.listarTodos();
    }

    @PutMapping()
    public ResponseEntity<?> actualizarBicicleta (@RequestBody Bicicleta bicicletaActualizada) throws Exception {
        bicicletaService.actualizar(bicicletaActualizada);
        return ResponseEntity.ok("Bicicleta actualizada.");
    }
}
