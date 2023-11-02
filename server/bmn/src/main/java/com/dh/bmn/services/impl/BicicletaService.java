package com.dh.bmn.services.impl;

import com.dh.bmn.dtos.requests.BicicletaRequestDto;

import com.dh.bmn.dtos.responses.BicicletaResponseDto;
import com.dh.bmn.entity.CaracteristicaBicicleta;
import com.dh.bmn.entity.Imagen;
import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.exceptions.RequestValidationException;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.IBicicletaRepository;
import com.dh.bmn.repositories.ICaracteristicaBicicletaRepository;
import com.dh.bmn.services.ICaracteristicaBicicletaService;
import com.dh.bmn.services.IService;
import com.dh.bmn.util.MapperClass;
import com.dh.bmn.pagging.PaginatedResponse;
import com.dh.bmn.pagging.PaginationData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BicicletaService implements IService<BicicletaResponseDto, BicicletaRequestDto>, ICaracteristicaBicicletaService {


    private final IBicicletaRepository bicicletaRepository;

    private final S3Service s3Service;

    private final ICaracteristicaBicicletaRepository caracteristicaBicicletaRepository;

    private static final ObjectMapper objectMapper = MapperClass.objectMapper();


    @Autowired
    public BicicletaService(IBicicletaRepository bicicletaRepository, S3Service s3Service, ICaracteristicaBicicletaRepository caracteristicaBicicletaRepository) {
        this.bicicletaRepository = bicicletaRepository;
        this.s3Service = s3Service;
        this.caracteristicaBicicletaRepository = caracteristicaBicicletaRepository;
    }

    @Override
    public void actualizar(BicicletaRequestDto bicicletaRequestDto) {
        Bicicleta bicicletaDB = bicicletaRepository.findById(bicicletaRequestDto.getBicicletaId()).orElseThrow(() -> new ResourceNotFoundException("La bicicleta no existe", HttpStatus.NOT_FOUND.value()));

        if (bicicletaDB != null) {

            normalizarNombreDescripcion(bicicletaRequestDto);
            bicicletaDB = objectMapper.convertValue(bicicletaRequestDto, Bicicleta.class);

            validarListaImagenesVacia(bicicletaRequestDto);
            validarYguardarImagenesBicicleta(bicicletaRequestDto, bicicletaDB);
            bicicletaRepository.save(bicicletaDB);
        }
    }

    @Transactional
    @Override
    public BicicletaResponseDto buscarPorId(Long id) {
        Bicicleta bicicleta = bicicletaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La bicicleta no existe", HttpStatus.NOT_FOUND.value()));
        return objectMapper.convertValue(bicicleta, BicicletaResponseDto.class);

    }

    @Override
    public void crear(BicicletaRequestDto bicicletaRequestDto) {
        normalizarNombreDescripcion(bicicletaRequestDto);

        if (bicicletaRepository.findByNombre(bicicletaRequestDto.getNombre()).isPresent()) {
            throw new ResourceAlreadyExistsException("La bicicleta ya existe", HttpStatus.CONFLICT.value());
        }

        Bicicleta bicicleta = objectMapper.convertValue(bicicletaRequestDto, Bicicleta.class);

        validarListaImagenesVacia(bicicletaRequestDto);

        validarYguardarImagenesBicicleta(bicicletaRequestDto, bicicleta);
        bicicletaRepository.save(bicicleta);
    }

    @Override
    public void borrarPorId(Long id) {
        Bicicleta bicicleta = bicicletaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La bicicleta no existe", HttpStatus.NOT_FOUND.value()));
        bicicletaRepository.delete(bicicleta);
    }

    @Override
    public List<BicicletaResponseDto> listarTodos() {
        List<Bicicleta> listaBicicletas = Optional.of(bicicletaRepository.findAll()).orElseThrow(() -> new ResourceNotFoundException("No se encontraron bicicletas", HttpStatus.NOT_FOUND.value()));

        return listaBicicletas
                .stream()
                .map(bicicleta -> objectMapper.convertValue(bicicleta, BicicletaResponseDto.class))
                .collect(Collectors.toList());
    }

    private void validarYguardarImagenesBicicleta(BicicletaRequestDto bicicletaRequestDto, Bicicleta bicicleta) {

        for (Imagen imagen : bicicletaRequestDto.getImagenes()) {
            s3Service.validarKey(imagen.getKey());
            s3Service.validarUrlNula(imagen.getUrl());
            imagen.setBicicleta(bicicleta);
        }

        bicicleta.setImagenes(bicicletaRequestDto.getImagenes());
    }

    private void validarListaImagenesVacia(BicicletaRequestDto bicicletaRequestDto) {

        if (bicicletaRequestDto.getImagenes().isEmpty()) {
            throw new RequestValidationException("La lista de imagenes no puede estar vacia", HttpStatus.BAD_REQUEST.value());
        }
    }

    private void normalizarNombreDescripcion(BicicletaRequestDto bicicletaRequestDto){

        String inicialNombre = bicicletaRequestDto.getNombre().substring(0, 1);
        String restoNombre = bicicletaRequestDto.getNombre().substring(1);
        bicicletaRequestDto.setNombre(inicialNombre.toUpperCase() + restoNombre.toLowerCase());

        String inicialDescripcion = bicicletaRequestDto.getDescripcion().substring(0, 1);
        String restoDescripcion = bicicletaRequestDto.getDescripcion().substring(1);
        bicicletaRequestDto.setDescripcion(inicialDescripcion.toUpperCase() + restoDescripcion.toLowerCase());
    }
    public PaginatedResponse<BicicletaResponseDto> obtenerPaginacion(int numeroPagina, int limit, int offset) {
        //Pageable pageable = PageRequest.of(numeroPagina - 1, limit);

        if (offset < 0) {
            offset = 0;
        }

        Pageable pageable = PageRequest.of(offset / limit, limit);

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

    public void agregarCaracteristicaABicicleta(Long bicicletaId, Long caracteristicaId) {
        Bicicleta bicicleta = bicicletaRepository.findById(bicicletaId)
                .orElseThrow(() -> new ResourceNotFoundException("Bicicleta no encontrada con ID: " + bicicletaId, HttpStatus.NOT_FOUND.value()));

        CaracteristicaBicicleta caracteristica = caracteristicaBicicletaRepository.findById(caracteristicaId)
                .orElseThrow(() -> new ResourceNotFoundException("Característica no encontrada con ID: " + caracteristicaId, HttpStatus.NOT_FOUND.value()));

        bicicleta.addCaracteristica(caracteristica);
        bicicletaRepository.save(bicicleta);
    }

    public void quitarCaracteristicaDeBicicleta(Long bicicletaId, Long caracteristicaId) {
        Bicicleta bicicleta = bicicletaRepository.findById(bicicletaId)
                .orElseThrow(() -> new ResourceNotFoundException("Bicicleta no encontrada con ID: " + bicicletaId, HttpStatus.NOT_FOUND.value()));

        CaracteristicaBicicleta caracteristica = caracteristicaBicicletaRepository.findById(caracteristicaId)
                .orElseThrow(() -> new ResourceNotFoundException("Característica no encontrada con ID: " + caracteristicaId, HttpStatus.NOT_FOUND.value()));

        bicicleta.removeCaracteristica(caracteristica);
        bicicletaRepository.save(bicicleta);
    }

}
