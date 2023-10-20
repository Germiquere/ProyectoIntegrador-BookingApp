package com.dh.bmn.service.impl;

import com.dh.bmn.dto.requests.ReservaRequestDto;
import com.dh.bmn.dto.responses.ReservaResponseDto;
import com.dh.bmn.entity.Reserva;
import com.dh.bmn.repository.IReservaRepository;
import com.dh.bmn.service.IService;
import com.dh.bmn.util.MapperClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaService implements IService<ReservaResponseDto, ReservaRequestDto> {

    @Autowired
    private final IReservaRepository reservaRepository;

    private static final ObjectMapper objectMapper = MapperClass.objectMapper();

    public ReservaService(IReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    private static final Logger LOGGER = LogManager.getLogger(UsuarioService.class);

    @Override
    public void actualizar(ReservaRequestDto reservaRequestDto) throws Exception {

    }

    @Override
    public Optional<ReservaResponseDto> buscarPorId(Long id) throws Exception {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if(reserva.isPresent()) {
            return reserva.stream().map(r-> objectMapper.convertValue(r, ReservaResponseDto.class)).findFirst();
        } else {
            throw new RuntimeException();
            //throw new NotFoundException("Código 201", "No se encontró el paciente con el ID: " + id);
        }
    }

    @Override
    public void guardar(ReservaRequestDto reservaRequestDto) throws Exception {

        Reserva reserva = objectMapper.convertValue(reservaRequestDto, Reserva.class);
        reservaRepository.save(reserva);
        LOGGER.info("Se creó una nueva reserva: " + reserva.toString());
    }

    @Override
    public void borrarPorId(Long id) throws Exception {
        Optional<Reserva> optionalReserva = reservaRepository.findById(id);
        if (optionalReserva.isPresent()) {
            reservaRepository.deleteById(id);
            LOGGER.info("Se eliminó la reserva con el ID: " + id);
        } else {
            throw new RuntimeException();
            //throw new NotFoundException("Código 201", "No se encontró la bicicleta con el ID: " + id);
        }
    }

    @Override
    public List<ReservaResponseDto> listarTodas() throws Exception {
        List<Reserva> reservas = reservaRepository.findAll();
        return reservas.stream().map(r -> objectMapper.convertValue(r, ReservaResponseDto.class)).collect(Collectors.toList());
    }
}
