package com.dh.bmn.controllers;

import com.dh.bmn.dtos.JsonMessageDto;
import com.dh.bmn.dtos.requests.CaracteristicaBicicletaRequestDto;
import com.dh.bmn.dtos.responses.CaracteristicaBicicletaResponseDto;
import com.dh.bmn.services.IService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bike-me-now/api/caracteristicas")

public class CaracteristicaBicicletaController {

    private final IService<CaracteristicaBicicletaResponseDto, CaracteristicaBicicletaRequestDto> caracteristicaBicicletaService;

    @Autowired
    public CaracteristicaBicicletaController(IService<CaracteristicaBicicletaResponseDto, CaracteristicaBicicletaRequestDto> caracteristicaBicicletaService) {
        this.caracteristicaBicicletaService = caracteristicaBicicletaService;
    }

    @GetMapping("/{id}")
    @Secured({ "ADMIN", "USER" })
    public ResponseEntity<CaracteristicaBicicletaResponseDto> obtenerCaracteristicaPorId (@PathVariable Long id) {
        return new ResponseEntity<>(caracteristicaBicicletaService.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    @Secured("ADMIN")
    public ResponseEntity<?> registrarCaracteristica (@RequestBody @Valid CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto){
        caracteristicaBicicletaService.crear(caracteristicaBicicletaRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Nueva característica de bicicleta registrada.",HttpStatus.CREATED.value()) , HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    public ResponseEntity<?> eliminarCaracteristicaPorId (@PathVariable Long id){
        caracteristicaBicicletaService.borrarPorId(id);
        return new ResponseEntity<>(new JsonMessageDto("Característica de bicicleta eliminada.",HttpStatus.OK.value()), HttpStatus.OK);
    }

    @GetMapping
    @Secured({ "ADMIN", "USER" })
    public ResponseEntity<List<CaracteristicaBicicletaResponseDto>> listaDeCaracteristicas(){
        return new ResponseEntity<>(caracteristicaBicicletaService.listarTodos(), HttpStatus.OK);
    }

    @PutMapping()
    @Secured("ADMIN")
    public ResponseEntity<?> actualizarCaracteristica (@RequestBody @Valid CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto){
        caracteristicaBicicletaService.actualizar(caracteristicaBicicletaRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Característica de bicicleta actualizada.",HttpStatus.OK.value()), HttpStatus.OK);
    }

}
