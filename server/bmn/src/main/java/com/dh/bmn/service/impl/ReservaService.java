package com.dh.bmn.service.impl;

import com.dh.bmn.dto.requests.ReservaRequestDto;
import com.dh.bmn.dto.responses.ReservaResponseDto;
import com.dh.bmn.entity.Reserva;
<<<<<<< HEAD
=======
import com.dh.bmn.dto.requests.ReservaRequestDto;
>>>>>>> 9c9bbdd5138d4d98b3958af2ff7ccbebc0f7e8f6
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
<<<<<<< HEAD
public class ReservaService implements IService<ReservaResponseDto, ReservaRequestDto> {
=======
public class ReservaService implements IService<Reserva, ReservaRequestDto> {
>>>>>>> 9c9bbdd5138d4d98b3958af2ff7ccbebc0f7e8f6

    @Autowired
    private final IReservaRepository reservaRepository;

<<<<<<< HEAD
    private static final ObjectMapper objectMapper = MapperClass.objectMapper();

    public ReservaService(IReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }
=======
//    @Autowired
//    private final ObjectMapper mapper;

    private static final ObjectMapper objectMapper = MapperClass.objectMapper();
>>>>>>> 9c9bbdd5138d4d98b3958af2ff7ccbebc0f7e8f6

    private static final Logger LOGGER = LogManager.getLogger(UsuarioService.class);

    @Override
    public void actualizar(ReservaRequestDto reservaRequestDto) throws Exception {

    }

    @Override
<<<<<<< HEAD
    public Optional<ReservaResponseDto> buscarPorId(Long id) throws Exception {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if(reserva.isPresent()) {
            return reserva.stream().map(r-> objectMapper.convertValue(r, ReservaResponseDto.class)).findFirst();
=======
    public Optional<ReservaRequestDto> buscarPorId(Integer id) throws Exception {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if(reserva.isPresent()) {
            return reserva.stream().map(r-> objectMapper.convertValue(r, ReservaRequestDto.class)).findFirst();
>>>>>>> 9c9bbdd5138d4d98b3958af2ff7ccbebc0f7e8f6
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
<<<<<<< HEAD
    public List<ReservaResponseDto> listarTodas() throws Exception {
        List<Reserva> reservas = reservaRepository.findAll();
        return reservas.stream().map(r -> objectMapper.convertValue(r, ReservaResponseDto.class)).collect(Collectors.toList());
=======
    public Set<ReservaRequestDto> listarTodas() throws Exception {
        List<Reserva> reservas = reservaRepository.findAll();
        return reservas.stream().map(r-> objectMapper.convertValue(r, ReservaRequestDto.class)).collect(Collectors.toSet());
>>>>>>> 9c9bbdd5138d4d98b3958af2ff7ccbebc0f7e8f6
    }
}
