package com.dh.bmn.services.impl;

import com.dh.bmn.dtos.requests.CaracteristicaBicicletaRequestDto;
import com.dh.bmn.dtos.responses.CaracteristicaBicicletaResponseDto;
import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.entity.CaracteristicaBicicleta;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.pagging.PaginatedResponse;
import com.dh.bmn.repositories.IBicicletaRepository;
import com.dh.bmn.repositories.ICaracteristicaBicicletaRepository;
import com.dh.bmn.services.IService;
import com.dh.bmn.util.MapperClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CaracteristicaBicicletaService implements IService<CaracteristicaBicicletaResponseDto, CaracteristicaBicicletaRequestDto> {

    private final ICaracteristicaBicicletaRepository caracteristicaBicicletaRepository;

    private static final ObjectMapper objectMapper = MapperClass.objectMapper();

    private final IBicicletaRepository bicicletaRepository;

    public CaracteristicaBicicletaService(ICaracteristicaBicicletaRepository caracteristicaBicicletaRepository,IBicicletaRepository biciletaRepository ) {
        this.caracteristicaBicicletaRepository = caracteristicaBicicletaRepository;
        this.bicicletaRepository = biciletaRepository;
    }

    @Override
    public void actualizar(CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto) {
        CaracteristicaBicicleta caracteristicaDB = caracteristicaBicicletaRepository.findById(caracteristicaBicicletaRequestDto.getCaracteristicaId()).orElseThrow(() -> new ResourceNotFoundException("La característica no existe", HttpStatus.NOT_FOUND.value()));

        if (caracteristicaDB != null) {

            normalizarNombre (caracteristicaBicicletaRequestDto);
            caracteristicaDB = objectMapper.convertValue(caracteristicaBicicletaRequestDto, CaracteristicaBicicleta.class);

            caracteristicaBicicletaRepository.save(caracteristicaDB);
        }
    }

    @Transactional
    @Override
    public CaracteristicaBicicletaResponseDto buscarPorId(Long id) {
        CaracteristicaBicicleta caracteristicaBicicleta = caracteristicaBicicletaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La caracteristica especificada no existe", HttpStatus.NOT_FOUND.value()));
        return objectMapper.convertValue(caracteristicaBicicleta, CaracteristicaBicicletaResponseDto.class);
    }

    @Override
    public void crear(CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto) {
        normalizarNombre(caracteristicaBicicletaRequestDto);

        if (caracteristicaBicicletaRepository.findByNombre(caracteristicaBicicletaRequestDto.getNombre()).isPresent()) {
            throw new ResourceAlreadyExistsException("La característica ya existe", HttpStatus.CONFLICT.value());
        }

        CaracteristicaBicicleta caracteristica = objectMapper.convertValue(caracteristicaBicicletaRequestDto, CaracteristicaBicicleta.class);
        caracteristicaBicicletaRepository.save(caracteristica);
    }

    @Override
    public void borrarPorId(Long id) {
        CaracteristicaBicicleta caracteristica = caracteristicaBicicletaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La característica no existe", HttpStatus.NOT_FOUND.value()));

        List<Bicicleta> bicicletasAsociadas = caracteristica.getBicicletas();

        for (Bicicleta bicicleta : bicicletasAsociadas) {
            bicicleta.getCaracteristicas().remove(caracteristica);
            bicicletaRepository.save(bicicleta);
        }

        caracteristicaBicicletaRepository.delete(caracteristica);
    }

    @Override
    public List<CaracteristicaBicicletaResponseDto> listarTodos() {
        List<CaracteristicaBicicleta> caracteristicaBicicletas = Optional.of(caracteristicaBicicletaRepository.findAll()).orElseThrow(() -> new ResourceNotFoundException("No se encontró ninguna característica de bicicleta", HttpStatus.NOT_FOUND.value()));
        return caracteristicaBicicletas.stream().map(categoria -> objectMapper.convertValue(categoria, CaracteristicaBicicletaResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public PaginatedResponse<CaracteristicaBicicletaResponseDto> obtenerPaginacion(int numeroPagina, int limit, int offset) {
        return null;
    }

    private void normalizarNombre(CaracteristicaBicicletaRequestDto caracteristicaBicicletaRequestDto) {

        String inicialNombre = caracteristicaBicicletaRequestDto.getNombre().substring(0, 1);
        String restoNombre = caracteristicaBicicletaRequestDto.getNombre().substring(1);
        caracteristicaBicicletaRequestDto.setNombre(inicialNombre.toUpperCase() + restoNombre.toLowerCase());

    }
}
