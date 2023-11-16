package com.dh.bmn.services.impl;

import com.dh.bmn.dtos.requests.PoliticaRequestDto;
import com.dh.bmn.dtos.responses.PoliticaResponseDto;
import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.entity.Politica;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.pagging.PaginatedResponse;
import com.dh.bmn.repositories.IBicicletaRepository;
import com.dh.bmn.repositories.IPoliticaRepository;
import com.dh.bmn.services.IService;
import com.dh.bmn.util.MapperClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PoliticaService implements IService<PoliticaResponseDto, PoliticaRequestDto> {

    private final IPoliticaRepository politicaRepository;

    private static final ObjectMapper objectMapper = MapperClass.objectMapper();

    private final IBicicletaRepository bicicletaRepository;

    @Autowired
    public PoliticaService(IPoliticaRepository politicaRepository, IBicicletaRepository biciletaRepository) {
        this.politicaRepository = politicaRepository;
        this.bicicletaRepository = biciletaRepository;
    }

    @Override
    public void actualizar(PoliticaRequestDto politicaRequestDto) {
        Politica politicaDB = politicaRepository.findById(politicaRequestDto.getPoliticaId()).orElseThrow(() -> new ResourceNotFoundException("La política no existe", HttpStatus.NOT_FOUND.value()));

        if (politicaDB != null) {

            normalizarNombreDescripcion(politicaRequestDto);
            politicaDB = objectMapper.convertValue(politicaRequestDto, Politica.class);
            politicaRepository.save(politicaDB);
        }
    }

    @Transactional
    @Override
    public PoliticaResponseDto buscarPorId(Long id) {
        Politica politica= politicaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La Política solicitada no existe", HttpStatus.NOT_FOUND.value()));
        return objectMapper.convertValue(politica, PoliticaResponseDto.class);
    }

    @Override
    public void crear(PoliticaRequestDto politicaRequestDto) {
        normalizarNombreDescripcion(politicaRequestDto);

        if (politicaRepository.findByTitulo(politicaRequestDto.getTitulo()).isPresent()) {
            throw new ResourceAlreadyExistsException("La política que estas intentando crear ya existe", HttpStatus.CONFLICT.value());
        }

        Politica politica = objectMapper.convertValue(politicaRequestDto, Politica.class);
        politicaRepository.save(politica);
    }

    @Override
    public void borrarPorId(Long id) {
        Politica politica = politicaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La política solicitada no existe", HttpStatus.NOT_FOUND.value()));

        List<Bicicleta> bicicletasAsociadas = politica.getBicicletas();

        for (Bicicleta bicicleta : bicicletasAsociadas) {
            bicicleta.getPoliticas().remove(politica);
            bicicletaRepository.save(bicicleta);
        }

        politicaRepository.delete(politica);
    }

    @Override
    public List<PoliticaResponseDto> listarTodos() {
        List<Politica> listaPoliticas = Optional.of(politicaRepository.findAll()).orElseThrow(() -> new ResourceNotFoundException("No se encontraron políticas", HttpStatus.NOT_FOUND.value()));

        return listaPoliticas
                .stream()
                .map(politica -> objectMapper.convertValue(politica, PoliticaResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PaginatedResponse<PoliticaResponseDto> obtenerPaginacion(int numeroPagina, int limit, int offset) {
        return null;
    }

    private void normalizarNombreDescripcion(PoliticaRequestDto politicaRequestDto){

        String inicialTitulo = politicaRequestDto.getTitulo().substring(0, 1);
        String restoTitulo = politicaRequestDto.getTitulo().substring(1);
        politicaRequestDto.setTitulo(inicialTitulo.toUpperCase() + restoTitulo.toLowerCase());

        String inicialDescripcion = politicaRequestDto.getDescripcion().substring(0, 1);
        String restoDescripcion = politicaRequestDto.getDescripcion().substring(1);
        politicaRequestDto.setDescripcion(inicialDescripcion.toUpperCase() + restoDescripcion.toLowerCase());
    }
}
