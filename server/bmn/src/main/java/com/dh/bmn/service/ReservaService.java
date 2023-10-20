package com.dh.bmn.service;

import com.dh.bmn.entity.Reserva;
import com.dh.bmn.dto.ReservaDto;
import com.dh.bmn.repository.IReservaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReservaService implements IService<Reserva, ReservaDto> {

    @Autowired
    private final IReservaRepository reservaRepository;
    @Autowired
    private final ObjectMapper mapper;

    private static final Logger LOGGER = LogManager.getLogger(UsuarioService.class);

    @Override
    public void actualizar(Reserva reserva) throws Exception {

    }

    @Override
    public Optional<ReservaDto> buscarPorId(Integer id) throws Exception {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if(reserva.isPresent()) {
            return reserva.stream().map(r-> mapper.convertValue(r, ReservaDto.class)).findFirst();
        } else {
            throw new RuntimeException();
            //throw new NotFoundException("Código 201", "No se encontró el paciente con el ID: " + id);
        }
    }

    @Override
    public void guardar(Reserva nuevaReserva) throws Exception {

        reservaRepository.save(nuevaReserva);
        LOGGER.info("Se creó una nueva reserva: " + nuevaReserva.toString());
    }

    @Override
    public void borrarPorId(Integer id) throws Exception {
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
    public Set<ReservaDto> listarTodos() throws Exception {
        List<Reserva> reservas = reservaRepository.findAll();
        return reservas.stream().map(r-> mapper.convertValue(r, ReservaDto.class)).collect(Collectors.toSet());
    }
}
