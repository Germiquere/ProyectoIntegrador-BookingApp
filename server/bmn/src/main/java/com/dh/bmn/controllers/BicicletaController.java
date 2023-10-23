package com.dh.bmn.controllers;

import com.dh.bmn.dtos.JsonMessageDto;
import com.dh.bmn.dtos.requests.BicicletaRequestDto;
import com.dh.bmn.dtos.responses.BicicletaResponseDto;
import com.dh.bmn.services.IService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bike-me-now/bicicletas")
public class BicicletaController {


    private final IService<BicicletaResponseDto, BicicletaRequestDto> bicicletaService;

    @Autowired
    public BicicletaController(IService<BicicletaResponseDto, BicicletaRequestDto> bicicletaService) {
        this.bicicletaService = bicicletaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BicicletaResponseDto> obtenerBicicletaPorId (@PathVariable Long id) {
        return new ResponseEntity<>(bicicletaService.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> registrarBicicleta (@RequestBody @Valid BicicletaRequestDto bicicletaRequestDto){
        bicicletaService.crear(bicicletaRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Nueva bicicleta registrada",HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarBicicletaPorId (@PathVariable Long id){
        bicicletaService.borrarPorId(id);
        return new ResponseEntity<>(new JsonMessageDto("Bicicleta eliminada exitosamente", HttpStatus.OK.value()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BicicletaResponseDto>> listarBicicletas (){
        return new ResponseEntity<>(bicicletaService.listarTodos(),HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> actualizarBicicleta (@RequestBody @Valid BicicletaRequestDto bicicletaRequestDto){
        bicicletaService.actualizar(bicicletaRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Bicicleta actualizada exitosamente", HttpStatus.OK.value()), HttpStatus.OK);
    }
}
