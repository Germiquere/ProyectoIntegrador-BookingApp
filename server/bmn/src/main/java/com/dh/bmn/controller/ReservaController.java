package com.dh.bmn.controller;

import com.dh.bmn.dto.requests.ReservaRequestDto;
import com.dh.bmn.entity.Reserva;
import com.dh.bmn.service.impl.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private final ReservaService reservaService;

    @Autowired
    private final ObjectMapper mapper;

    public ReservaController(ReservaService reservaService, ObjectMapper mapper) {
        this.reservaService = reservaService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public Optional<ReservaRequestDto> obtenerReservaPorId (@PathVariable Integer id) throws Exception{
        return reservaService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> registrarReserva (@RequestBody Reserva nuevaReserva) throws Exception {
        reservaService.guardar(nuevaReserva);
        return ResponseEntity.ok("Nueva reserva registradoa.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarReservaPorId(@PathVariable Integer id) throws Exception {
        reservaService.borrarPorId(id);
        return ResponseEntity.ok("Reserva eliminada.");
    }

    @GetMapping
    public Set<ReservaRequestDto> listaDeReservas () throws Exception {
        return reservaService.listarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarReserva(@RequestBody Reserva reservaActualizada) throws Exception {
        reservaService.actualizar(reservaActualizada);
        return ResponseEntity.ok("Reserva actualizada.");
    }
}
