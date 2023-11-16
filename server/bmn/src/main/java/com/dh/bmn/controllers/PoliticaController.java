package com.dh.bmn.controllers;

import com.dh.bmn.dtos.JsonMessageDto;
import com.dh.bmn.dtos.requests.PoliticaRequestDto;
import com.dh.bmn.dtos.responses.PoliticaResponseDto;
import com.dh.bmn.services.IService;
import com.dh.bmn.services.impl.BicicletaService;
import com.dh.bmn.services.impl.PoliticaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bike-me-now")
public class PoliticaController {

    private final PoliticaService politicaService;

    public PoliticaController(PoliticaService politicaService, BicicletaService biciletaService, BicicletaService bicicletaService, IService<PoliticaResponseDto, PoliticaRequestDto> genericService) {
        this.politicaService = politicaService;
    }


    @GetMapping("/politicas/{id}")
    @Secured({ "ADMIN", "USER" })
    public ResponseEntity<PoliticaResponseDto> obtenerPoliticaPorId (@PathVariable Long id) {
        return new ResponseEntity<>(politicaService.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping("/api/politicas")
    @Secured("ADMIN")
    public ResponseEntity<?> registrarPolitica (@RequestBody @Valid PoliticaRequestDto politicaRequestDto){
        politicaService.crear(politicaRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Nueva política registrada.",HttpStatus.CREATED.value()) , HttpStatus.CREATED);
    }

    @DeleteMapping("/api/politicas/{id}")
    @Secured("ADMIN")
    public ResponseEntity<?> eliminarPoliticaPorId (@PathVariable Long id){
        politicaService.borrarPorId(id);
        return new ResponseEntity<>(new JsonMessageDto("Política eliminada.",HttpStatus.OK.value()), HttpStatus.OK);
    }

    @GetMapping ("/politicas")
    @Secured({ "ADMIN", "USER" })
    public ResponseEntity<List<PoliticaResponseDto>> listarPoliticas(){
        return new ResponseEntity<>(politicaService.listarTodos(), HttpStatus.OK);
    }

    @PutMapping ("/api/politicas")
    @Secured("ADMIN")
    public ResponseEntity<?> actualizarPolitica (@RequestBody @Valid PoliticaRequestDto politicaRequestDto){
        politicaService.actualizar(politicaRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Política actualizada.",HttpStatus.OK.value()), HttpStatus.OK);
    }
}
