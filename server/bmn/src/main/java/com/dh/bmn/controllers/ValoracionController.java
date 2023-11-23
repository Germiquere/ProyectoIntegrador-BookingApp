package com.dh.bmn.controllers;

import com.dh.bmn.dtos.JsonMessageDto;
import com.dh.bmn.dtos.requests.ValoracionRequestDto;
import com.dh.bmn.dtos.responses.ValoracionResponseDto;
import com.dh.bmn.services.impl.ValoracionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bike-me-now/api/valoraciones")
public class ValoracionController {

    private final ValoracionService valoracionService;

    @Autowired
    public ValoracionController(ValoracionService valoracionService) {
        this.valoracionService = valoracionService;
    }

    @GetMapping("/{id}")
    @Secured({ "ADMIN", "USER" })
    public ResponseEntity<ValoracionResponseDto> obtenerValoracionPorId (@PathVariable Long id) {
        return new ResponseEntity<>(valoracionService.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    @Secured({ "USER" })
    public ResponseEntity<?> registrarValoracion (@RequestBody @Valid ValoracionRequestDto valoracionRequestDto){
        valoracionService.crear(valoracionRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Nueva valoración registrada",HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Secured({ "ADMIN", "USER" })
    public ResponseEntity<?> eliminarValoracionPorId (@PathVariable Long id){
        valoracionService.borrarPorId(id);
        return new ResponseEntity<>(new JsonMessageDto("Valoracion eliminada exitosamente",HttpStatus.OK.value()), HttpStatus.OK);
    }

    @GetMapping
    @Secured("ADMIN")
    public ResponseEntity<List<ValoracionResponseDto>> listarValoraciones (){
        return new ResponseEntity<>(valoracionService.listarTodos(),HttpStatus.OK);
    }

    @PutMapping
    @Secured({ "ADMIN", "USER" })
    public ResponseEntity<?> actualizarValoracion (@RequestBody @Valid ValoracionRequestDto valoracionRequestDto){
        valoracionService.actualizar(valoracionRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Valoracion actualizado exitosamente",HttpStatus.OK.value()), HttpStatus.OK);
    }

}
