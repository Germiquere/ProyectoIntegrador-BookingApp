package com.dh.bmn.controllers;

import com.dh.bmn.dtos.JsonMessageDto;
import com.dh.bmn.dtos.requests.ReservaRequestDto;
import com.dh.bmn.dtos.responses.ReservaResponseDto;
import com.dh.bmn.services.IService;
import com.dh.bmn.services.impl.ReservaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bike-me-now/api/reservas")
public class ReservaController {

    private final IService<ReservaResponseDto, ReservaRequestDto> iReservaService;

    private final ReservaService reservaService;

    @Autowired
    public ReservaController(IService<ReservaResponseDto, ReservaRequestDto> iReservaService, ReservaService reservaService) {
        this.iReservaService = iReservaService;
        this.reservaService = reservaService;
    }

    @GetMapping("/{id}")
    @Secured({ "ADMIN", "USER" })
    public ResponseEntity<ReservaResponseDto> obtenerReservaPorId (@PathVariable Long id)  {
        return new ResponseEntity<>(iReservaService.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    @Secured({ "ADMIN", "USER" })
    public ResponseEntity<?> registrarReserva (@RequestBody @Valid ReservaRequestDto reservaRequestDto) {
        iReservaService.crear(reservaRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Nueva reserva registrada",HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Secured({ "ADMIN", "USER" })
    public ResponseEntity<?> eliminarReservaPorId (@PathVariable Long id) {
        iReservaService.borrarPorId(id);
        return new ResponseEntity<>(new JsonMessageDto("Reserva eliminada exitosamente",HttpStatus.OK.value()), HttpStatus.OK);
    }

    @GetMapping
    @Secured("ADMIN")
    public ResponseEntity<List<ReservaResponseDto>> listarReservas () {
        return new ResponseEntity<>(iReservaService.listarTodos(),HttpStatus.OK);
    }

    @PutMapping()
    @Secured({ "ADMIN", "USER" })
    public ResponseEntity<?> actualizarReserva (@RequestBody @Valid ReservaRequestDto reservaRequestDto) {
        iReservaService.actualizar(reservaRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Reserva actualizada exitosamente",HttpStatus.OK.value()), HttpStatus.OK);
    }

    @GetMapping("/usuarios/{id}")
    @Secured("ADMIN")
    public ResponseEntity<List<ReservaResponseDto>> obtenerReservasPorUsuario(@PathVariable Long id) {
        return new ResponseEntity<>(reservaService.obtenerReservasPorUsuario(id), HttpStatus.OK);
    }
}
