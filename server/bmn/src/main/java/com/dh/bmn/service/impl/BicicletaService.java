package com.dh.bmn.service.impl;

import com.dh.bmn.dto.requests.BicicletaRequestDto;
import com.dh.bmn.dto.responses.BicicletaResponseDto;
import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.repository.IBicicletaRepository;
import com.dh.bmn.service.IService;
import com.dh.bmn.util.MapperClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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

<<<<<<< HEAD
=======
    private static final Logger LOGGER = LogManager.getLogger(BicicletaService.class);

>>>>>>> 9c9bbdd5138d4d98b3958af2ff7ccbebc0f7e8f6
    public BicicletaService(IBicicletaRepository bicicletaRepository) {
        this.bicicletaRepository = bicicletaRepository;
    }

    @Override
    public void actualizar(BicicletaRequestDto bicicletaRequestDto) throws Exception {

    }

    @Override
    public BicicletaResponseDto buscarPorId(Long id) throws Exception {

        Bicicleta bicicleta = bicicletaRepository.findById(id).orElseThrow(RuntimeException::new);
        return objectMapper.convertValue(bicicleta, BicicletaResponseDto.class);
    }

    @Override
    public void guardar(BicicletaRequestDto bicicletaRequestDto) throws Exception {
        String inicialNombre = bicicletaRequestDto.getNombre().substring(0, 1);
        String restoNombre = bicicletaRequestDto.getNombre().substring(1);
        bicicletaRequestDto.setNombre(inicialNombre.toUpperCase() + restoNombre.toLowerCase());

        Bicicleta bicicleta = objectMapper.convertValue(bicicletaRequestDto, Bicicleta.class);
        bicicletaRepository.save(bicicleta);
        //LOGGER.info("Se creó una nueva bicicleta: " + nuevoBicicleta.toString());
    }

    @Override
    public void borrarPorId(Long id) throws Exception {
        Optional<Bicicleta> optionalBicicleta = bicicletaRepository.findById(id);
        if (optionalBicicleta.isPresent()) {
            bicicletaRepository.deleteById(id);
            LOGGER.info("Se eliminó la bicicleta con el ID: " + id);
        } else {
            throw new RuntimeException();
            //throw new NotFoundException("Código 201", "No se encontró la bicicleta con el ID: " + id);
        }
    }

    @Override
    public List<BicicletaResponseDto> listarTodas() throws Exception {
        List<Bicicleta> bicicletas = bicicletaRepository.findAll();
        return bicicletas.stream().map(bicicleta -> objectMapper.convertValue(bicicleta, BicicletaResponseDto.class)).collect(Collectors.toList());
    }
}
