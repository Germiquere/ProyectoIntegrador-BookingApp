package com.dh.bmn.controller;

import com.dh.bmn.dto.requests.ReservaRequestDto;
<<<<<<< HEAD
import com.dh.bmn.dto.responses.ReservaResponseDto;
import com.dh.bmn.service.IService;
=======
import com.dh.bmn.entity.Reserva;
import com.dh.bmn.service.impl.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;
>>>>>>> 9c9bbdd5138d4d98b3958af2ff7ccbebc0f7e8f6
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
<<<<<<< HEAD
    public ResponseEntity<Optional<ReservaResponseDto>> obtenerReservaPorId (@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(reservaService.buscarPorId(id), HttpStatus.OK);
=======
    public Optional<ReservaRequestDto> obtenerReservaPorId (@PathVariable Integer id) throws Exception{
        return reservaService.buscarPorId(id);
>>>>>>> 9c9bbdd5138d4d98b3958af2ff7ccbebc0f7e8f6
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
<<<<<<< HEAD
    public ResponseEntity<List<ReservaResponseDto>> listarReservas () throws Exception {
        return new ResponseEntity<>(reservaService.listarTodas(),HttpStatus.OK);
=======
    public Set<ReservaRequestDto> listaDeReservas () throws Exception {
        return reservaService.listarTodos();
>>>>>>> 9c9bbdd5138d4d98b3958af2ff7ccbebc0f7e8f6
    }

    @PutMapping()
    public ResponseEntity<?> actualizarReserva (@RequestBody ReservaRequestDto reservaRequestDto) throws Exception {
        reservaService.actualizar(reservaRequestDto);
        return new ResponseEntity<>("Reserva actualizada exitosamente", HttpStatus.OK);
    }
}
