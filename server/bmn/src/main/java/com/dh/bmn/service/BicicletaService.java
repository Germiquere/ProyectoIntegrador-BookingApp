package com.dh.bmn.service;

import com.dh.bmn.dto.BicicletaDto;
import com.dh.bmn.entity.Bicicleta;
import com.dh.bmn.repository.IBicicletaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BicicletaService implements IService<Bicicleta, BicicletaDto>{

    @Autowired
    private final IBicicletaRepository bicicletaRepository;
    @Autowired
    private final ObjectMapper mapper;

    public BicicletaService(IBicicletaRepository bicicletaRepository, ObjectMapper mapper) {
        this.bicicletaRepository = bicicletaRepository;
        this.mapper = new ObjectMapper();
    }

    private static final Logger LOGGER = LogManager.getLogger(BicicletaService.class);

    @Override
    public void actualizar(Bicicleta bicicleta) throws Exception {

    }

    @Override
    public Optional<BicicletaDto> buscarPorId(Integer id) throws Exception {
        return Optional.empty();
    }

    @Override
    public void guardar(Bicicleta nuevaBicicleta) throws Exception {
        String inicialNombre = nuevaBicicleta.getNombre().substring(0, 1);
        String restoNombre = nuevaBicicleta.getNombre().substring(1);
        nuevaBicicleta.setNombre(inicialNombre.toUpperCase() + restoNombre.toLowerCase());

        bicicletaRepository.save(nuevaBicicleta);
        //LOGGER.info("Se creó una nueva bicicleta: " + nuevoBicicleta.toString());
    }

    @Override
    public void borrarPorId(Integer id) throws Exception {
        Optional<Bicicleta> optionalBicicleta = bicicletaRepository.findById(id);
        if (optionalBicicleta.isPresent()) {
            bicicletaRepository.deleteById(id);
            //LOGGER.info("Se eliminó la bicicleta con el ID: " + id);
        } else {
            throw new RuntimeException();
            //throw new NotFoundException("Código 201", "No se encontró la bicicleta con el ID: " + id);
        }
    }

    @Override
    public Set<BicicletaDto> listarTodos() throws Exception {
        List<Bicicleta> bicicletas = bicicletaRepository.findAll();
        return bicicletas.stream().map(b-> mapper.convertValue(b, BicicletaDto.class)).collect(Collectors.toSet());
    }
}
