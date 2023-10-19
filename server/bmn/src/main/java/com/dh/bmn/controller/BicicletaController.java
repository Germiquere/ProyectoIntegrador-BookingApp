package com.dh.bmn.controller;

import com.dh.bmn.dto.requests.BicicletaRequestDto;
import com.dh.bmn.dto.responses.BicicletaResponseDto;
import com.dh.bmn.service.impl.BicicletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/bicicletas")
public class BicicletaController {

    @Autowired
    private final BicicletaService bicicletaService;

    public BicicletaController(BicicletaService bicicletaService) {
        this.bicicletaService = bicicletaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BicicletaResponseDto> obtenerBicicletaPorId (@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(bicicletaService.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> registrarBicicleta (@RequestBody BicicletaRequestDto bicicletaRequestDto) throws Exception {
        bicicletaService.guardar(bicicletaRequestDto);
        return new ResponseEntity<>("Nueva bicicleta registrada", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarBicicletaPorId (@PathVariable Long id) throws Exception {
        bicicletaService.borrarPorId(id);
        return new ResponseEntity<>("Bicicleta eliminada exitosamente", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BicicletaResponseDto>> listarBicicletas () throws Exception {
        return new ResponseEntity<>(bicicletaService.listarTodos(),HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> actualizarBicicleta (@RequestBody BicicletaRequestDto bicicletaRequestDto) throws Exception {
        bicicletaService.actualizar(bicicletaRequestDto);
        return new ResponseEntity<>("Bicicleta actualizada exitosamente", HttpStatus.OK);
    }
}
