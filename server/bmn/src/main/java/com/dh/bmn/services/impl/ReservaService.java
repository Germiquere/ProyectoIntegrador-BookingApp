package com.dh.bmn.services.impl;

import com.dh.bmn.dtos.requests.ReservaRequestDto;
import com.dh.bmn.dtos.responses.ReservaResponseDto;
import com.dh.bmn.entity.Reserva;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.IReservaRepository;
import com.dh.bmn.services.IService;
import com.dh.bmn.util.MapperClass;
import com.dh.bmn.pagging.PaginatedResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaService implements IService<ReservaResponseDto, ReservaRequestDto> {

    private final IReservaRepository reservaRepository;

    private final UsuarioService usuarioService;

    private final BicicletaService bicicletaService;

    private static final ObjectMapper objectMapper = MapperClass.objectMapper();


    @Autowired
    public ReservaService(IReservaRepository reservaRepository, UsuarioService usuarioService, BicicletaService bicicletaService) {
        this.reservaRepository = reservaRepository;
        this.usuarioService = usuarioService;
        this.bicicletaService = bicicletaService;
    }

    @Override
    public void actualizar(ReservaRequestDto reservaRequestDto) {

        usuarioService.buscarPorId(reservaRequestDto.getUsuario().getUsuarioId());
        bicicletaService.buscarPorId(reservaRequestDto.getBicicleta().getBicicletaId());

        Reserva reservaDB = reservaRepository.findById(reservaRequestDto.getReservaId()).orElseThrow(() -> new ResourceNotFoundException("La reserva no existe", HttpStatus.NOT_FOUND.value()));

        if (reservaDB != null) {
            reservaDB = objectMapper.convertValue(reservaRequestDto, Reserva.class);

            reservaRepository.save(reservaDB);
        }
    }

    @Override
    public ReservaResponseDto buscarPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La reserva no existe", HttpStatus.NOT_FOUND.value()));
        return objectMapper.convertValue(reserva, ReservaResponseDto.class);

    }

    @Override
    public void crear(ReservaRequestDto reservaRequestDto) {

        if (reservaRepository.findByBicicletaAndFechaInicioAndFechaFin(reservaRequestDto.getBicicleta().getBicicletaId(), reservaRequestDto.getFechaInicio(), reservaRequestDto.getFechaFin()).isPresent()) {
            throw new ResourceAlreadyExistsException("La reserva ya existe", HttpStatus.CONFLICT.value());
        }

        Reserva reserva = objectMapper.convertValue(reservaRequestDto, Reserva.class);

        reservaRepository.save(reserva);
    }

    @Override
    public void borrarPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La reserva no existe", HttpStatus.NOT_FOUND.value()));
        reservaRepository.delete(reserva);
    }

    @Override
    public List<ReservaResponseDto> listarTodos() {
        List<Reserva> reservas = Optional.of(reservaRepository.findAll()).orElseThrow(() -> new ResourceNotFoundException("No se encontraron reservas", HttpStatus.NOT_FOUND.value()));
        return reservas.stream().map(reserva -> objectMapper.convertValue(reserva, ReservaResponseDto.class)).collect(Collectors.toList());

    }

    @Override
    public PaginatedResponse<ReservaResponseDto> obtenerPaginacion(int numeroPagina, int limit, int offset) {
        return null;
    }

    public List<ReservaResponseDto> obtenerReservasPorUsuario(Long usuarioId) {

        usuarioService.buscarPorId(usuarioId);

        List<Reserva> reservas =  Optional.of(reservaRepository.findReservasByUsuarioId(usuarioId)).orElseThrow(() -> new ResourceNotFoundException("No hay reservas registradas para ese usuario", HttpStatus.NOT_FOUND.value()));
        return reservas
                .stream()
                .map(reserva -> objectMapper.convertValue(reserva, ReservaResponseDto.class))
                .collect(Collectors.toList());




    }
}
