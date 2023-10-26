package com.dh.bmn.services.impl;

import com.dh.bmn.dtos.requests.BicicletaRequestDto;
import com.dh.bmn.dtos.responses.BicicletaResponseDto;
import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.IBicicletaRepository;
import com.dh.bmn.services.IService;
import com.dh.bmn.util.MapperClass;
import com.dh.bmn.util.PaginatedResponse;
import com.dh.bmn.util.PaginationData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BicicletaService implements IService<BicicletaResponseDto, BicicletaRequestDto> {

    @Autowired
    private final IBicicletaRepository bicicletaRepository;

    private static final ObjectMapper objectMapper = MapperClass.objectMapper();


    @Autowired
    public BicicletaService(IBicicletaRepository bicicletaRepository) {
        this.bicicletaRepository = bicicletaRepository;
    }

    @Override
    public void actualizar(BicicletaRequestDto bicicletaRequestDto){
        Bicicleta bicicletaDB = bicicletaRepository.findById(bicicletaRequestDto.getBicicletaId()).orElseThrow(() -> new ResourceNotFoundException("La bicicleta no existe", HttpStatus.NOT_FOUND.value()));

        if (bicicletaDB != null) {
            bicicletaDB = objectMapper.convertValue(bicicletaRequestDto, Bicicleta.class);

            bicicletaRepository.save(bicicletaDB);
        }
    }

    @Override
    public BicicletaResponseDto buscarPorId(Long id) {
        Bicicleta bicicleta = bicicletaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La bicicleta no existe", HttpStatus.NOT_FOUND.value()));
        return objectMapper.convertValue(bicicleta, BicicletaResponseDto.class);

    }

    @Override
    public void crear(BicicletaRequestDto bicicletaRequestDto){
//        String inicialNombre = bicicletaRequestDto.getNombre().substring(0, 1);
//        String restoNombre = bicicletaRequestDto.getNombre().substring(1);
//        bicicletaRequestDto.setNombre(inicialNombre.toUpperCase() + restoNombre.toLowerCase());

        if (bicicletaRepository.findByNombreAndDescripcion(bicicletaRequestDto.getNombre(), bicicletaRequestDto.getDescripcion()).isPresent()) {
            throw new ResourceAlreadyExistsException("La bicicleta ya existe", HttpStatus.CONFLICT.value());
        }
        Bicicleta bicicleta = objectMapper.convertValue(bicicletaRequestDto, Bicicleta.class);

        bicicletaRepository.save(bicicleta);
    }

    @Override
    public void borrarPorId(Long id){
        Bicicleta bicicleta = bicicletaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La bicicleta no existe", HttpStatus.NOT_FOUND.value()));
        bicicletaRepository.delete(bicicleta);
    }

    @Override
    public List<BicicletaResponseDto> listarTodos(){
        List<Bicicleta> listaBicicletas = Optional.of(bicicletaRepository.findAll()).orElseThrow(() -> new ResourceNotFoundException("No se encontraron bicicletas", HttpStatus.NOT_FOUND.value()));

        return listaBicicletas
                .stream()
                .map(bicicleta -> objectMapper.convertValue(bicicleta, BicicletaResponseDto.class))
                .collect(Collectors.toList());
    }

    public PaginatedResponse<BicicletaResponseDto> obtenerPaginacion(int numeroPagina, int limit, int offset) {
        Pageable pageable = PageRequest.of(numeroPagina - 1, limit);

        if (offset < 0) {
            offset = 0;
        }

        pageable = PageRequest.of(offset / limit, limit);

        Page<Bicicleta> bicicletas = bicicletaRepository.findAll(pageable);

        List<BicicletaResponseDto> bicicletasDtoList = bicicletas.getContent().stream()
                .map(bicicleta -> objectMapper.convertValue(bicicleta, BicicletaResponseDto.class))
                .collect(Collectors.toList());

        PaginationData paginationData = new PaginationData();
        paginationData.setTotal(bicicletas.getTotalElements());
        paginationData.setPrimary_results(bicicletas.getNumberOfElements());
        paginationData.setOffset(offset);
        paginationData.setLimit(bicicletas.getPageable().getPageSize());

        return new PaginatedResponse<>(bicicletasDtoList, paginationData);
    }

}
