package com.dh.bmn.services.impl;

import com.dh.bmn.dtos.requests.ReservaRequestDto;
import com.dh.bmn.dtos.responses.ReservaResponseDto;
import com.dh.bmn.entity.Reserva;
import com.dh.bmn.exceptions.IllegalDateException;
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
import java.time.LocalDate;
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

            List<Reserva> listaReservasBicicleta = reservaRepository.findReservasByBicicletaId(reservaRequestDto.getBicicleta().getBicicletaId());

            if(!listaReservasBicicleta.isEmpty()){
                validarSiExisteReservaPrevia(reservaRequestDto);
            }

            validarFechaInicioFinValida(reservaRequestDto.getFechaInicio(), reservaRequestDto.getFechaFin());

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

        validarSiExisteReservaPrevia(reservaRequestDto);

        validarFechaInicioFinValida(reservaRequestDto.getFechaInicio(), reservaRequestDto.getFechaFin());
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

//    //AGREGADO
//    @Override
//    public ReservaResponseDto buscarPorToken(String token) {
//        return null;
//    }

    public List<ReservaResponseDto> obtenerReservasPorUsuario(Long usuarioId) {

        usuarioService.buscarPorId(usuarioId);

        List<Reserva> reservas =  Optional.of(reservaRepository.findReservasByUsuarioId(usuarioId)).orElseThrow(() -> new ResourceNotFoundException("No hay reservas registradas para ese usuario", HttpStatus.NOT_FOUND.value()));
        return reservas
                .stream()
                .map(reserva -> objectMapper.convertValue(reserva, ReservaResponseDto.class))
                .collect(Collectors.toList());

    }

    public List<ReservaResponseDto> obtenerReservasPorBicicleta(Long bicicletaId) {

        bicicletaService.buscarPorId(bicicletaId);

        List<Reserva> reservas =  Optional.of(reservaRepository.findReservasByBicicletaId(bicicletaId)).orElseThrow(() -> new ResourceNotFoundException("No hay reservas registradas para esa bicicleta", HttpStatus.NOT_FOUND.value()));
        return reservas
                .stream()
                .map(reserva -> objectMapper.convertValue(reserva, ReservaResponseDto.class))
                .collect(Collectors.toList());

    }

    private void validarSiExisteReservaPrevia(ReservaRequestDto reservaRequestDto){

        if (reservaRepository.findByBicicletaAndFechaInicioAndFechaFin(reservaRequestDto.getBicicleta().getBicicletaId(), reservaRequestDto.getFechaInicio(), reservaRequestDto.getFechaFin()).isPresent()) {
            throw new ResourceAlreadyExistsException("Ya existe una reserva en esa fecha para la bicicleta especificada", HttpStatus.CONFLICT.value());
        }
    }

    private void validarFechaInicioFinValida(LocalDate fechaInicio, LocalDate fechaFin){
        if ( fechaInicio.isBefore(LocalDate.now())) {
            throw new IllegalDateException("La fecha de inicio no puede ser menor a la actual", HttpStatus.BAD_REQUEST.value());
        }

        if ( fechaFin.isBefore(fechaInicio)) {
            throw new IllegalDateException("La fecha de fin no puede ser menor a la fecha de inicio", HttpStatus.BAD_REQUEST.value());
        }
    }

    public boolean isConcluida(Reserva reserva) {
        LocalDate today = LocalDate.now();
        return reserva.getFechaFin() != null && reserva.getFechaFin().isBefore(today);
    }

    public List<Reserva> obtenerReservasConcluidasPorUsuario(Long usuarioId) {
        List<Reserva> reservas = reservaRepository.findReservasByUsuarioId(usuarioId);
        System.out.println(" las reservas del usuario son: " + reservas);
        return reservas.stream()
                .filter(this::isConcluida)
                .collect(Collectors.toList());
    }
}
