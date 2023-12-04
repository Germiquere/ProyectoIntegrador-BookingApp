package com.dh.bmn.services.impl;

import com.dh.bmn.dtos.requests.ReservaRequestDto;
import com.dh.bmn.dtos.requests.ValoracionRequestDto;
import com.dh.bmn.dtos.responses.ValoracionResponseDto;
import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.entity.Reserva;
import com.dh.bmn.entity.Usuario;
import com.dh.bmn.entity.Valoracion;
import com.dh.bmn.exceptions.IllegalDateException;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.pagging.PaginatedResponse;
import com.dh.bmn.repositories.IBicicletaRepository;
import com.dh.bmn.repositories.IReservaRepository;
import com.dh.bmn.repositories.IUsuarioRepository;
import com.dh.bmn.repositories.IValoracionRepository;
import com.dh.bmn.services.IService;
import com.dh.bmn.util.MapperClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ValoracionService implements IService<ValoracionResponseDto, ValoracionRequestDto> {

    private final IValoracionRepository valoracionRepository;
    private final IReservaRepository reservaRepository;
    private final IBicicletaRepository bicicletaRepository;
    private final UsuarioService usuarioService;
    private final IUsuarioRepository usuarioRepository;
    private final BicicletaService bicicletaService;
    private final ReservaService reservaService;


    private static final ObjectMapper objectMapper = MapperClass.objectMapper();

    @Autowired
    public ValoracionService(IValoracionRepository valoracionRepository, IReservaRepository reservaRepository, IBicicletaRepository bicicletaRepository, UsuarioService usuarioService, IUsuarioRepository usuarioRepository, BicicletaService bicicletaService, ReservaService reservaService) {
        this.valoracionRepository = valoracionRepository;
        this.reservaRepository = reservaRepository;
        this.bicicletaRepository = bicicletaRepository;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.bicicletaService = bicicletaService;
        this.reservaService = reservaService;
    }

    public List<ValoracionResponseDto> obtenerValoracionesPorBicicleta(Long bicicletaId) {

        bicicletaService.buscarPorId(bicicletaId);

        List<Valoracion> valoraciones = Optional.of(valoracionRepository.findByBicicletaId(bicicletaId)).orElseThrow(() -> new ResourceNotFoundException("No hay valoraciones registradas para esa bicicleta", HttpStatus.NOT_FOUND.value()));
        return valoraciones
                .stream()
                .map(valoracion -> objectMapper.convertValue(valoracion, ValoracionResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void actualizar(ValoracionRequestDto valoracionRequestDto) {
        Valoracion valoracion = valoracionRepository.findById(valoracionRequestDto.getValoracionId()).orElseThrow(() -> new ResourceNotFoundException("La valoración solicitada no existe", HttpStatus.NOT_FOUND.value()));
        if (valoracion != null) {
            valoracion = objectMapper.convertValue(valoracionRequestDto, Valoracion.class);
            valoracionRepository.save(valoracion);
            bicicletaService.actualizarPromedioPuntuacion(valoracion.getBicicleta());
        }
    }

    @Transactional
    @Override
    public ValoracionResponseDto buscarPorId(Long id) {
        Valoracion valoracion = valoracionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La valoración solicitada no existe", HttpStatus.NOT_FOUND.value()));
        return objectMapper.convertValue(valoracion, ValoracionResponseDto.class);
    }

    @Override
    public void crear(ValoracionRequestDto valoracionRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuario = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo electrónico: " + emailUsuario));

        ReservaRequestDto reservaRequestDto = valoracionRequestDto.getReserva();
        if (reservaRequestDto == null || reservaRequestDto.getReservaId() == null) {
            throw new NullPointerException("La solicitud no contiene información de reserva válida. No se puede crear la valoración.");
        }

        Reserva reserva = reservaRepository.findById(reservaRequestDto.getReservaId())
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + reservaRequestDto.getReservaId(), HttpStatus.CONFLICT.value()));

        if (!reservaService.isConcluida(reserva)) {
            throw new IllegalDateException("No se puede crear la valoración, la reserva no esta concluida", HttpStatus.BAD_REQUEST.value());
        }

        if (valoracionRepository.existsByUsuarioAndReserva(usuario, reserva)) {
            throw new ResourceAlreadyExistsException("Ya existe una valoración para esta reserva y usuario.", HttpStatus.BAD_REQUEST.value());
        }

        Bicicleta bicicleta = reserva.getBicicleta();

        Valoracion valoracion = objectMapper.convertValue(valoracionRequestDto, Valoracion.class);
        valoracion.setUsuario(usuario);
        valoracion.setBicicleta(bicicleta);
        valoracion.setReserva(reserva);

        valoracionRepository.save(valoracion);
        bicicletaService.actualizarPromedioPuntuacion(bicicleta);
    }

    @Override
    public void borrarPorId(Long id) {
        Valoracion valoracion = valoracionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La valoracion solicitada no existe", HttpStatus.NOT_FOUND.value()));
        valoracionRepository.delete(valoracion);
        bicicletaService.actualizarPromedioPuntuacion(valoracion.getBicicleta());
    }

    @Override
    public List<ValoracionResponseDto> listarTodos() {
        List<Valoracion> listaValoraciones = Optional.of(valoracionRepository.findAll()).orElseThrow(() -> new ResourceNotFoundException("No se encontraron valoraciones", HttpStatus.NOT_FOUND.value()));

        return listaValoraciones
                .stream()
                .map(valoracion -> objectMapper.convertValue(valoracion, ValoracionResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PaginatedResponse<ValoracionResponseDto> obtenerPaginacion(int numeroPagina, int limit, int offset) {
        return null;
    }
}
