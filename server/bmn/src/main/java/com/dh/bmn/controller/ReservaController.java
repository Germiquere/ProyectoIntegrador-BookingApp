package com.dh.bmn.controller;

import com.dh.bmn.dto.requests.ReservaRequestDto;
import com.dh.bmn.dto.responses.ReservaResponseDto;
import com.dh.bmn.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bike-me-now/reservas")
public class ReservaController {

    private final IService<ReservaResponseDto, ReservaRequestDto> reservaService;

    @Autowired
    public ReservaController(IService<ReservaResponseDto, ReservaRequestDto> reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ReservaResponseDto>> obtenerReservaPorId (@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(reservaService.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> registrarReserva (@RequestBody ReservaRequestDto reservaRequestDto) throws Exception {
        reservaService.guardar(reservaRequestDto);
        return new ResponseEntity<>("Nueva reserva registrada", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReservaPorId (@PathVariable Long id) throws Exception {
        reservaService.borrarPorId(id);
        return new ResponseEntity<>("Reserva eliminada exitosamente", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDto>> listarReservas () throws Exception {
        return new ResponseEntity<>(reservaService.listarTodas(),HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> actualizarReserva (@RequestBody ReservaRequestDto reservaRequestDto) throws Exception {
        reservaService.actualizar(reservaRequestDto);
        return new ResponseEntity<>("Reserva actualizada exitosamente", HttpStatus.OK);
    }
}
