package com.dh.bmn.controllers;

import com.dh.bmn.dtos.JsonMessageDto;
import com.dh.bmn.dtos.requests.BicicletaRequestDto;
import com.dh.bmn.dtos.responses.BicicletaResponseDto;
import com.dh.bmn.services.IService;
import com.dh.bmn.pagging.PaginatedResponse;
import com.dh.bmn.services.impl.BicicletaService;
import com.dh.bmn.services.impl.CaracteristicaBicicletaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/bike-me-now")
public class BicicletaController {

    private final BicicletaService bicicletaService;

    @Autowired
    public BicicletaController(BicicletaService bicicletaService) {
        this.bicicletaService = bicicletaService;
    }

    @GetMapping("/bicicletas/{id}")
    public ResponseEntity<BicicletaResponseDto> obtenerBicicletaPorId (@PathVariable Long id) {
        return new ResponseEntity<>(bicicletaService.buscarPorId(id), HttpStatus.OK);
    }


    @PostMapping("/api/bicicletas")
    @Secured("ADMIN")
    public ResponseEntity<?> registrarBicicleta (@RequestBody @Valid BicicletaRequestDto bicicletaRequestDto){
        bicicletaService.crear(bicicletaRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Nueva bicicleta registrada",HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/bicicletas/{id}")
    @Secured("ADMIN")
    public ResponseEntity<?> eliminarBicicletaPorId (@PathVariable Long id){
        bicicletaService.borrarPorId(id);
        return new ResponseEntity<>(new JsonMessageDto("Bicicleta eliminada exitosamente", HttpStatus.OK.value()), HttpStatus.OK);
    }

    @GetMapping("/bicicletas")
    public ResponseEntity<List<BicicletaResponseDto>> listarBicicletas (){
        return new ResponseEntity<>(bicicletaService.listarTodos(),HttpStatus.OK);
    }

    @PutMapping("/api/bicicletas")
    @Secured("ADMIN")
    public ResponseEntity<?> actualizarBicicleta (@RequestBody @Valid BicicletaRequestDto bicicletaRequestDto){
        bicicletaService.actualizar(bicicletaRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Bicicleta actualizada exitosamente", HttpStatus.OK.value()), HttpStatus.OK);
    }

    @GetMapping("/bicicletas/search")
    public ResponseEntity<PaginatedResponse<BicicletaResponseDto>> buscarBicicletas(
            @RequestParam String query,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        PaginatedResponse<BicicletaResponseDto> paginatedResponse = bicicletaService.buscarBicicletas(query, fechaInicio, fechaFin, limit, offset);
        return new ResponseEntity<>(paginatedResponse, HttpStatus.OK);
    }

}
