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
@RequestMapping("/bike-me-now")
public class ReservaController {

    //private final IService<ReservaResponseDto, ReservaRequestDto> iReservaService;

    private final ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping("api/reservas/{id}")
    @Secured({ "ADMIN", "USER" })
    public ResponseEntity<ReservaResponseDto> obtenerReservaPorId (@PathVariable Long id)  {
        return new ResponseEntity<>(reservaService.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping("/api/reservas")
    @Secured({ "ADMIN", "USER" })
    public ResponseEntity<?> registrarReserva (@RequestBody @Valid ReservaRequestDto reservaRequestDto) {
        reservaService.crear(reservaRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Nueva reserva registrada",HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/reservas/{id}")
    @Secured({ "ADMIN", "USER" })
    public ResponseEntity<?> eliminarReservaPorId (@PathVariable Long id) {
        reservaService.borrarPorId(id);
        return new ResponseEntity<>(new JsonMessageDto("Reserva eliminada exitosamente",HttpStatus.OK.value()), HttpStatus.OK);
    }

    @GetMapping("/api/reservas")
    @Secured({"ADMIN", "USER"})
    public ResponseEntity<List<ReservaResponseDto>> listarReservas () {
        return new ResponseEntity<>(reservaService.listarTodos(),HttpStatus.OK);
    }

    @PutMapping("/api/reservas")
    @Secured({ "ADMIN", "USER"})
    public ResponseEntity<?> actualizarReserva (@RequestBody @Valid ReservaRequestDto reservaRequestDto) {
        reservaService.actualizar(reservaRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Reserva actualizada exitosamente",HttpStatus.OK.value()), HttpStatus.OK);
    }

    @GetMapping("/api/reservas/usuarios/{id}")
    @Secured("ADMIN")
    public ResponseEntity<List<ReservaResponseDto>> obtenerReservasPorUsuario(@PathVariable Long id) {
        return new ResponseEntity<>(reservaService.obtenerReservasPorUsuario(id), HttpStatus.OK);
    }

    @GetMapping("/reservas/bicicletas/{id}")
    public ResponseEntity<List<ReservaResponseDto>> obtenerReservasPorBicicleta(@PathVariable Long id) {
        return new ResponseEntity<>(reservaService.obtenerReservasPorBicicleta(id), HttpStatus.OK);
    }
}
