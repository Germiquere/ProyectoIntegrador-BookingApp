package com.dh.bmn.services.impl;

import com.dh.bmn.dtos.requests.BicicletaRequestDto;
import com.dh.bmn.dtos.responses.BicicletaResponseDto;
import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.repositories.IBicicletaRepository;
import com.dh.bmn.services.IService;
import com.dh.bmn.util.MapperClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    }

    @Override
    public BicicletaResponseDto buscarPorId(Long id) {
        Bicicleta bicicleta = bicicletaRepository.findById(id).orElseThrow(RuntimeException::new);
        return objectMapper.convertValue(bicicleta, BicicletaResponseDto.class);
    }

    @Override
    public void guardar(BicicletaRequestDto bicicletaRequestDto){
        String inicialNombre = bicicletaRequestDto.getNombre().substring(0, 1);
        String restoNombre = bicicletaRequestDto.getNombre().substring(1);
        bicicletaRequestDto.setNombre(inicialNombre.toUpperCase() + restoNombre.toLowerCase());

        Bicicleta bicicleta = objectMapper.convertValue(bicicletaRequestDto, Bicicleta.class);
        bicicletaRepository.save(bicicleta);
    }

    @Override
    public void borrarPorId(Long id){
        Optional<Bicicleta> optionalBicicleta = bicicletaRepository.findById(id);
        if (optionalBicicleta.isPresent()) {
            bicicletaRepository.deleteById(id);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public List<BicicletaResponseDto> listarTodos(){
        List<Bicicleta> bicicletas = bicicletaRepository.findAll();
        return bicicletas.stream().map(bicicleta -> objectMapper.convertValue(bicicleta, BicicletaResponseDto.class)).collect(Collectors.toList());
    }
}
