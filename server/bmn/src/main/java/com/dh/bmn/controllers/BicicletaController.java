package com.dh.bmn.controllers;

import com.dh.bmn.dtos.JsonMessageDto;
import com.dh.bmn.dtos.requests.BicicletaRequestDto;
import com.dh.bmn.dtos.responses.BicicletaResponseDto;
import com.dh.bmn.services.ICaracteristicaBicicletaService;
import com.dh.bmn.services.IService;
import com.dh.bmn.pagging.PaginatedResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/bike-me-now")
public class BicicletaController {


    private final IService<BicicletaResponseDto, BicicletaRequestDto> bicicletaService;
    private final ICaracteristicaBicicletaService caracteristicaBicicletaService;


    @Autowired
    public BicicletaController(IService<BicicletaResponseDto, BicicletaRequestDto> bicicletaService, ICaracteristicaBicicletaService caracteristicaBicicletaService) {
        this.bicicletaService = bicicletaService;
        this.caracteristicaBicicletaService = caracteristicaBicicletaService;
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

    @GetMapping("/bicicletas/page/{numeroPagina}")
    public ResponseEntity<PaginatedResponse<BicicletaResponseDto>> obtenerPaginaBicicletas(
            @PathVariable int numeroPagina,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        PaginatedResponse<BicicletaResponseDto> paginatedResponse = bicicletaService.obtenerPaginacion(numeroPagina, limit, offset);
        return new ResponseEntity<>(paginatedResponse, HttpStatus.OK);
    }

    @PostMapping("/{bicicletaId}/caracteristicas/{caracteristicaId}")
    public ResponseEntity<String> agregarCaracteristicaABicicleta(@PathVariable Long bicicletaId, @PathVariable Long caracteristicaId) {
        caracteristicaBicicletaService.agregarCaracteristicaABicicleta(bicicletaId, caracteristicaId);
        return ResponseEntity.ok("Característica agregada a la bicicleta con éxito.");
    }

    @DeleteMapping("/{bicicletaId}/caracteristicas/{caracteristicaId}")
    public ResponseEntity<String> quitarCaracteristicaDeBicicleta(@PathVariable Long bicicletaId, @PathVariable Long caracteristicaId) {
        caracteristicaBicicletaService.quitarCaracteristicaDeBicicleta(bicicletaId, caracteristicaId);
        return ResponseEntity.ok("Característica quitada de la bicicleta con éxito.");
    }

    /*private ResponseEntity<PaginatedResponse<BicicletaResponseDto>> obtenerPaginacion(int numeroPagina, int limit, int offset) {
        PaginatedResponse<BicicletaResponseDto> paginatedResponse = bicicletaService.obtenerPaginacion(numeroPagina, limit, offset);
        return new ResponseEntity<>(paginatedResponse, HttpStatus.OK);
    }*/

    /*@GetMapping("/page/siguiente")
    public ResponseEntity<PaginatedResponse<BicicletaResponseDto>> obtenerSiguientePaginaBicicletas(
            @RequestParam(defaultValue = "1") int numeroPagina,
            @RequestParam(defaultValue = "10") int elementosPorPagina) {
        numeroPagina++; // Obtener la página siguiente
        return obtenerPagina(numeroPagina, elementosPorPagina);
    }

    @GetMapping("/page/anterior")
    public ResponseEntity<PaginatedResponse<BicicletaResponseDto>> obtenerPaginaAnteriorBicicletas(
            @RequestParam(defaultValue = "1") int numeroPagina,
            @RequestParam(defaultValue = "10") int elementosPorPagina) {
        numeroPagina--; // Obtener la página anterior
        return obtenerPagina(numeroPagina, elementosPorPagina);
    }

    @GetMapping("/page/inicio")
    public ResponseEntity<PaginatedResponse<BicicletaResponseDto>> obtenerPrimeraPaginaBicicletas(
            @RequestParam(defaultValue = "10") int elementosPorPagina) {
        int numeroPagina = 1; // Obtener la primera página
        return obtenerPagina(numeroPagina, elementosPorPagina);
    }*/

}
