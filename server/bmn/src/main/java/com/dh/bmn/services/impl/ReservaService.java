package com.dh.bmn.services.impl;

import com.dh.bmn.dtos.requests.ReservaRequestDto;
import com.dh.bmn.dtos.responses.ReservaResponseDto;
import com.dh.bmn.entity.Reserva;
import com.dh.bmn.repositories.IReservaRepository;
import com.dh.bmn.services.IService;
import com.dh.bmn.util.MapperClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaService implements IService<ReservaResponseDto, ReservaRequestDto> {

    private final IReservaRepository reservaRepository;

    private static final ObjectMapper objectMapper = MapperClass.objectMapper();

    @Autowired
    public ReservaService(IReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Override
    public void actualizar(ReservaRequestDto reservaRequestDto) {

    }

    @Override
    public ReservaResponseDto buscarPorId(Long id){
        Reserva reserva = reservaRepository.findById(id).orElseThrow(RuntimeException::new);
        return objectMapper.convertValue(reserva, ReservaResponseDto.class);
    }

        @Override
        public void guardar (ReservaRequestDto reservaRequestDto){

            Reserva reserva = objectMapper.convertValue(reservaRequestDto, Reserva.class);
            reservaRepository.save(reserva);
        }

        @Override
        public void borrarPorId (Long id) {
            Optional<Reserva> optionalReserva = reservaRepository.findById(id);
            if (optionalReserva.isPresent()) {
                reservaRepository.deleteById(id);
            } else {
                throw new RuntimeException();
            }
        }

        @Override
        public List<ReservaResponseDto> listarTodos () {
            List<Reserva> reservas = reservaRepository.findAll();
            return reservas.stream().map(r -> objectMapper.convertValue(r, ReservaResponseDto.class)).collect(Collectors.toList());
        }
    }
