package com.dh.bmn.services.impl;

import com.dh.bmn.dtos.requests.BicicletaRequestDto;
import com.dh.bmn.dtos.requests.CaracteristicaBicicletaRequestDto;
import com.dh.bmn.dtos.requests.CategoriaBicicletaRequestDto;
import com.dh.bmn.dtos.requests.PoliticaRequestDto;
import com.dh.bmn.dtos.responses.BicicletaResponseDto;
import com.dh.bmn.entity.*;
import com.dh.bmn.exceptions.RequestValidationException;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.IBicicletaRepository;
import com.dh.bmn.repositories.ICaracteristicaBicicletaRepository;
import com.dh.bmn.repositories.ICategoriaBicicletaRepository;
import com.dh.bmn.repositories.IPoliticaRepository;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BicicletaService implements IService<BicicletaResponseDto, BicicletaRequestDto> {

    private final IBicicletaRepository bicicletaRepository;

    private final S3Service s3Service;

    private final ICaracteristicaBicicletaRepository caracteristicaBicicletaRepository;

    private final ICategoriaBicicletaRepository categoriaBicicletaRepository;

    private final IPoliticaRepository politicaRepository;

    private static final ObjectMapper objectMapper = MapperClass.objectMapper();

    @Autowired
    public BicicletaService(IBicicletaRepository bicicletaRepository, S3Service s3Service, ICaracteristicaBicicletaRepository caracteristicaBicicletaRepository, ICategoriaBicicletaRepository categoriaBicicletaRepository, IPoliticaRepository politicaRepository) {
        this.bicicletaRepository = bicicletaRepository;
        this.s3Service = s3Service;
        this.caracteristicaBicicletaRepository = caracteristicaBicicletaRepository;
        this.categoriaBicicletaRepository = categoriaBicicletaRepository;
        this.politicaRepository = politicaRepository;
    }

    @Override
    public void actualizar(BicicletaRequestDto bicicletaRequestDto) {
        Bicicleta bicicletaDB = bicicletaRepository.findById(bicicletaRequestDto.getBicicletaId()).orElseThrow(() -> new ResourceNotFoundException("La bicicleta no existe", HttpStatus.NOT_FOUND.value()));

        if (bicicletaDB != null) {

            normalizarNombreDescripcion(bicicletaRequestDto);
            bicicletaDB = objectMapper.convertValue(bicicletaRequestDto, Bicicleta.class);

            guardarCategoriasBicicleta(bicicletaRequestDto, bicicletaDB);
            guardarCaracteriticasBicicleta(bicicletaRequestDto, bicicletaDB);
            guardarPoliticas(bicicletaRequestDto, bicicletaDB);
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

        guardarCategoriasBicicleta(bicicletaRequestDto, bicicleta);
        guardarCaracteriticasBicicleta(bicicletaRequestDto, bicicleta);
        guardarPoliticas(bicicletaRequestDto, bicicleta);
        validarListaImagenesVacia(bicicletaRequestDto);
        validarYguardarImagenesBicicleta(bicicletaRequestDto, bicicleta);
        bicicletaRepository.save(bicicleta);
    }

    @Override
    public void borrarPorId(Long id) {
        Bicicleta bicicleta = bicicletaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La bicicleta no existe", HttpStatus.NOT_FOUND.value()));

        // Obtén las categorías asociadas a la bicicleta
        List<CategoriaBicicleta> categorias = bicicleta.getCategorias();

        // Elimina la bicicleta de las categorías asociadas
        for (CategoriaBicicleta categoria : categorias) {
            categoria.getBicicletas().remove(bicicleta);
            categoriaBicicletaRepository.save(categoria);
        }

        // Elimina la bicicleta
        bicicletaRepository.delete(bicicleta);
    }

    /*public void borrarPorId(Long id) {
        Bicicleta bicicleta = bicicletaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La bicicleta no existe", HttpStatus.NOT_FOUND.value()));
        bicicletaRepository.delete(bicicleta);
    }*/

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

    private void guardarCategoriasBicicleta(BicicletaRequestDto bicicletaRequestDto, Bicicleta bicicleta) {

        List<CategoriaBicicleta> categorias = new ArrayList<>();
        for (CategoriaBicicletaRequestDto categoriaBicicletaRequestDto : bicicletaRequestDto.getCategorias()) {
            Optional<CategoriaBicicleta> categoriaOptional = categoriaBicicletaRepository.findById(categoriaBicicletaRequestDto.getCategoriaId());
            CategoriaBicicleta categoria = categoriaOptional.orElseThrow(() -> new ResourceNotFoundException("La categoria con ID " + categoriaBicicletaRequestDto.getCategoriaId() + " no existe", HttpStatus.NOT_FOUND.value()));

            categorias.add(categoria);
        }

        bicicleta.setCategorias(categorias);
    }

    private void guardarCaracteriticasBicicleta(BicicletaRequestDto bicicletaRequestDto, Bicicleta bicicleta) {

        List<CaracteristicaBicicleta> caracteristicas = new ArrayList<>();
        for (CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto : bicicletaRequestDto.getCaracteristicas()) {
            Optional<CaracteristicaBicicleta> caracteristicaOptional = caracteristicaBicicletaRepository.findById(caracteristicaBicicletaRequestDto.getCaracteristicaId());
            CaracteristicaBicicleta caracteristica = caracteristicaOptional.orElseThrow(() -> new ResourceNotFoundException("La caracteristica con ID " + caracteristicaBicicletaRequestDto.getCaracteristicaId() + " no existe", HttpStatus.NOT_FOUND.value()));

            caracteristicas.add(caracteristica);
        }

        bicicleta.setCaracteristicas(caracteristicas);
    }


    private void guardarPoliticas(BicicletaRequestDto bicicletaRequestDto, Bicicleta bicicleta) {

        List<Politica> politicas = new ArrayList<>();
        for (PoliticaRequestDto politicaRequestDto : bicicletaRequestDto.getPoliticas()) {
            Optional<Politica> politicaOptional = politicaRepository.findById(politicaRequestDto.getPoliticaId());
            Politica politica = politicaOptional.orElseThrow(() -> new ResourceNotFoundException("La politica con ID " + politicaRequestDto.getPoliticaId() + " no existe", HttpStatus.NOT_FOUND.value()));

            politicas.add(politica);
        }

        bicicleta.setPoliticas(politicas);
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

    /*public PaginatedResponse<BicicletaResponseDto> buscarBicicletas(String query, int limit, int offset) {
        // Realiza la consulta sin paginación
        List<Bicicleta> resultados = bicicletaRepository.buscarBicicletasPorQuery(query);

        // Aplica la paginación manualmente
        int totalResultados = resultados.size();
        int endIndex = Math.min(offset + limit, totalResultados);
        List<Bicicleta> resultadosPaginados = resultados.subList(offset, endIndex);

        // Convierte la lista paginada de entidades a lista de DTOs
        List<BicicletaResponseDto> resultadosDto = resultadosPaginados.stream()
                .map(bicicleta -> objectMapper.convertValue(bicicleta, BicicletaResponseDto.class))
                .collect(Collectors.toList());

        // Crea y devuelve la respuesta paginada
        PaginationData paginationData = new PaginationData();
        paginationData.setTotal(totalResultados);
        paginationData.setPrimary_results(resultadosPaginados.size());
        paginationData.setOffset(offset);
        paginationData.setLimit(limit);

        return new PaginatedResponse<>(resultadosDto, paginationData);
    }*/

    @Transactional(readOnly = true)
    public PaginatedResponse<BicicletaResponseDto> buscarBicicletas(String query, int limit, int offset) {
        List<String> words = Arrays.asList(query.split("\\s+"));
        List<Bicicleta> resultados = bicicletaRepository.buscarBicicletasPorQuery(query, words);

        // Aplica la paginación manualmente
        int totalResultados = resultados.size();
        int endIndex = Math.min(offset + limit, totalResultados);
        List<Bicicleta> resultadosPaginados = resultados.subList(offset, endIndex);

        // Convierte la lista paginada de entidades a lista de DTOs
        List<BicicletaResponseDto> resultadosDto = resultadosPaginados.stream()
                .map(bicicleta -> objectMapper.convertValue(bicicleta, BicicletaResponseDto.class))
                .collect(Collectors.toList());

        // Crea y devuelve la respuesta paginada
        PaginationData paginationData = new PaginationData();
        paginationData.setTotal(totalResultados);
        paginationData.setPrimary_results(resultadosPaginados.size());
        paginationData.setOffset(offset);
        paginationData.setLimit(limit);


        paginationData.setTotal(totalResultados);
        //paginationData.setPrimary
        return new PaginatedResponse<>(resultadosDto, paginationData);
    }

}
