package com.dh.bmn.services.impl;

import com.dh.bmn.dtos.requests.CategoriaBicicletaRequestDto;
import com.dh.bmn.dtos.responses.CategoriaBicicletaResponseDto;
import com.dh.bmn.entity.CategoriaBicicleta;
import com.dh.bmn.repositories.ICategoriaBicicletaRepository;
import com.dh.bmn.services.IService;
import com.dh.bmn.util.MapperClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    }

    @Override
    public CategoriaBicicletaResponseDto buscarPorId(Long id) {
        CategoriaBicicleta categoriaBicicleta = categoriaBicicletaRepository.findById(id).orElseThrow(RuntimeException::new);
        return objectMapper.convertValue(categoriaBicicleta, CategoriaBicicletaResponseDto.class);
    }

    @Override
    public void guardar(CategoriaBicicletaRequestDto categoriaBicicletaRequestDto){
        String inicialNombre = categoriaBicicletaRequestDto.getNombre().substring(0, 1);
        String restoNombre = categoriaBicicletaRequestDto.getNombre().substring(1);
        categoriaBicicletaRequestDto.setNombre(inicialNombre.toUpperCase() + restoNombre.toLowerCase());

        CategoriaBicicleta categoria = objectMapper.convertValue(categoriaBicicletaRequestDto, CategoriaBicicleta.class);
        categoriaBicicletaRepository.save(categoria);
    }

    @Override
    public void borrarPorId(Long id){
        Optional<CategoriaBicicleta> optionalCategoriaBicicleta = categoriaBicicletaRepository.findById(id);
        if (optionalCategoriaBicicleta.isPresent()) {
            categoriaBicicletaRepository.deleteById(id);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public List<CategoriaBicicletaResponseDto> listarTodos() {
        List<CategoriaBicicleta> categoriasBicicletas = categoriaBicicletaRepository.findAll();
        return categoriasBicicletas.stream().map(categoria -> objectMapper.convertValue(categoria, CategoriaBicicletaResponseDto.class)).collect(Collectors.toList());
    }
}
