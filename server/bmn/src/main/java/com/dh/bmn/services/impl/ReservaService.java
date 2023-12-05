package com.dh.bmn.services.impl;

import com.dh.bmn.dtos.requests.ReservaRequestDto;
import com.dh.bmn.dtos.responses.ReservaResponseDto;
import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.entity.Reserva;
import com.dh.bmn.entity.Usuario;
import com.dh.bmn.exceptions.IllegalDateException;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.IBicicletaRepository;
import com.dh.bmn.repositories.IReservaRepository;
import com.dh.bmn.repositories.IUsuarioRepository;
import com.dh.bmn.services.IService;
import com.dh.bmn.util.MapperClass;
import com.dh.bmn.pagging.PaginatedResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
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

    private final IUsuarioRepository usuarioRepository;

    private final IBicicletaRepository bicicletaRepository;

    private final UsuarioService usuarioService;

    private final BicicletaService bicicletaService;

    private final EmailService emailService;

    private static final ObjectMapper objectMapper = MapperClass.objectMapper();


    @Autowired
    public ReservaService(IReservaRepository reservaRepository, IUsuarioRepository usuarioRepository, IBicicletaRepository bicicletaRepository, UsuarioService usuarioService, BicicletaService bicicletaService, EmailService emailService) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.bicicletaRepository = bicicletaRepository;
        this.usuarioService = usuarioService;
        this.bicicletaService = bicicletaService;
        this.emailService = emailService;
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

        Usuario usuario = usuarioRepository.findById(reservaRequestDto.getUsuario().getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe", HttpStatus.NOT_FOUND.value()));

        Bicicleta bicicleta = bicicletaRepository.findById(reservaRequestDto.getBicicleta().getBicicletaId())
                .orElseThrow(() -> new ResourceNotFoundException("La bicicleta no existe", HttpStatus.NOT_FOUND.value()));

        Reserva reserva = objectMapper.convertValue(reservaRequestDto, Reserva.class);
        reserva.setUsuario(usuario);
        reserva.setBicicleta(bicicleta);

        reservaRepository.save(reserva);

        try {
            emailService.sendReservationEmail(usuario.getEmail(), usuario.getNombre(), bicicleta.getNombre(), reservaRequestDto.getFechaInicio(), reservaRequestDto.getFechaFin());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
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
}
