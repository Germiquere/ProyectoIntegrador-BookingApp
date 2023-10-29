package com.dh.bmn.services.impl;

import com.dh.bmn.dtos.requests.CategoriaBicicletaRequestDto;
import com.dh.bmn.dtos.responses.CategoriaBicicletaResponseDto;
import com.dh.bmn.entity.CategoriaBicicleta;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.ICategoriaBicicletaRepository;
import com.dh.bmn.services.IService;
import com.dh.bmn.util.MapperClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaBicicletaService implements IService<CategoriaBicicletaResponseDto, CategoriaBicicletaRequestDto> {

    private final ICategoriaBicicletaRepository categoriaBicicletaRepository;

    private static final ObjectMapper objectMapper = MapperClass.objectMapper();

    @Autowired
    public CategoriaBicicletaService(ICategoriaBicicletaRepository categoriaBicicletaRepository) {
        this.categoriaBicicletaRepository = categoriaBicicletaRepository;
    }

    @Override
    public void actualizar(CategoriaBicicletaRequestDto categoriaBicicletaRequestDto){
        CategoriaBicicleta categoriaDB = categoriaBicicletaRepository.findById(categoriaBicicletaRequestDto.getCategoriaId()).orElseThrow(() -> new ResourceNotFoundException("La categoria no existe", HttpStatus.NOT_FOUND.value()));

        if (categoriaDB != null) {

            normalizarNombreDescripcion(categoriaBicicletaRequestDto);
            categoriaDB = objectMapper.convertValue(categoriaBicicletaRequestDto, CategoriaBicicleta.class);

            categoriaBicicletaRepository.save(categoriaDB);
        }
    }

    @Override
    public CategoriaBicicletaResponseDto buscarPorId(Long id) {
        CategoriaBicicleta categoriaBicicleta = categoriaBicicletaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La categoria especificada no existe", HttpStatus.NOT_FOUND.value()));
        return objectMapper.convertValue(categoriaBicicleta, CategoriaBicicletaResponseDto.class);
    }

    @Override
    public void crear(CategoriaBicicletaRequestDto categoriaBicicletaRequestDto){
        normalizarNombreDescripcion(categoriaBicicletaRequestDto);

        if (categoriaBicicletaRepository.findByNombre(categoriaBicicletaRequestDto.getNombre()).isPresent()) {
            throw new ResourceAlreadyExistsException("La categoria ya existe", HttpStatus.CONFLICT.value());
        }

        CategoriaBicicleta categoria = objectMapper.convertValue(categoriaBicicletaRequestDto, CategoriaBicicleta.class);
        categoriaBicicletaRepository.save(categoria);
    }

    @Override
    public void borrarPorId(Long id){
        CategoriaBicicleta categoria = categoriaBicicletaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La categoria no existe", HttpStatus.NOT_FOUND.value()));
        categoriaBicicletaRepository.delete(categoria);
    }

    @Override
    public List<CategoriaBicicletaResponseDto> listarTodos() {
        List<CategoriaBicicleta> categoriasBicicletas = Optional.of(categoriaBicicletaRepository.findAll()).orElseThrow(() -> new ResourceNotFoundException("No se encontro ninguna categoria de bicicleta", HttpStatus.NOT_FOUND.value()));
        return categoriasBicicletas.stream().map(categoria -> objectMapper.convertValue(categoria, CategoriaBicicletaResponseDto.class)).collect(Collectors.toList());
    }

    private void normalizarNombreDescripcion(CategoriaBicicletaRequestDto categoriaBicicletaRequestDto){

        String inicialNombre = categoriaBicicletaRequestDto.getNombre().substring(0, 1);
        String restoNombre = categoriaBicicletaRequestDto.getNombre().substring(1);
        categoriaBicicletaRequestDto.setNombre(inicialNombre.toUpperCase() + restoNombre.toLowerCase());

        String inicialDescripcion = categoriaBicicletaRequestDto.getDescripcion().substring(0, 1);
        String restoDescripcion = categoriaBicicletaRequestDto.getDescripcion().substring(1);
        categoriaBicicletaRequestDto.setDescripcion(inicialDescripcion.toUpperCase() + restoDescripcion.toLowerCase());
    }
}
