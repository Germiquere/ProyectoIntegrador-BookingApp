package com.dh.bmn.service.impl;

import com.dh.bmn.dto.requests.CategoriaBicicletaRequestDto;
import com.dh.bmn.dto.responses.CategoriaBicicletaResponseDto;
import com.dh.bmn.entity.CategoriaBicicleta;
import com.dh.bmn.repository.ICategoriaBicicletaRepository;
import com.dh.bmn.service.IService;
import com.dh.bmn.service.impl.BicicletaService;
import com.dh.bmn.util.MapperClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaBicicletaService implements IService<CategoriaBicicletaResponseDto, CategoriaBicicletaRequestDto> {

    @Autowired
    private final ICategoriaBicicletaRepository categoriaBicicletaRepository;

    private static final ObjectMapper objectMapper = MapperClass.objectMapper();

    public CategoriaBicicletaService(ICategoriaBicicletaRepository categoriaBicicletaRepository) {
        this.categoriaBicicletaRepository = categoriaBicicletaRepository;
    }

    private static final Logger LOGGER = LogManager.getLogger(BicicletaService.class);

    @Override
    public void actualizar(CategoriaBicicletaRequestDto categoriaBicicletaRequestDto) throws Exception {

    }

    @Override
    public CategoriaBicicletaResponseDto buscarPorId(Long id) throws Exception {

        CategoriaBicicleta categoriaBicicleta = categoriaBicicletaRepository.findById(id).orElseThrow(RuntimeException::new);

        return objectMapper.convertValue(categoriaBicicleta, CategoriaBicicletaResponseDto.class);

    }

    @Override
    public void guardar(CategoriaBicicletaRequestDto categoriaBicicletaRequestDto) throws Exception {
        String inicialNombre = categoriaBicicletaRequestDto.getNombre().substring(0, 1);
        String restoNombre = categoriaBicicletaRequestDto.getNombre().substring(1);
        categoriaBicicletaRequestDto.setNombre(inicialNombre.toUpperCase() + restoNombre.toLowerCase());

        CategoriaBicicleta categoria = objectMapper.convertValue(categoriaBicicletaRequestDto, CategoriaBicicleta.class);
        categoriaBicicletaRepository.save(categoria);
        //LOGGER.info("Se creó una nueva bicicleta: " + nuevoBicicleta.toString());
    }

    @Override
    public void borrarPorId(Long id) throws Exception {
        Optional<CategoriaBicicleta> optionalCategoriaBicicleta = categoriaBicicletaRepository.findById(id);
        if (optionalCategoriaBicicleta.isPresent()) {
            categoriaBicicletaRepository.deleteById(id);
            LOGGER.info("Se eliminó la categoría de bicicleta con el ID: " + id);
        } else {
            throw new RuntimeException();
            //throw new NotFoundException("Código 201", "No se encontró la bicicleta con el ID: " + id);
        }
    }

    @Override
    public List<CategoriaBicicletaResponseDto> listarTodas() throws Exception {
        List<CategoriaBicicleta> categoriasBicicletas = categoriaBicicletaRepository.findAll();
        return categoriasBicicletas.stream().map(categoria -> objectMapper.convertValue(categoria, CategoriaBicicletaResponseDto.class)).collect(Collectors.toList());
    }
}
