package com.dh.bmn.service;

import com.dh.bmn.dto.CategoriaBicicletaDto;
import com.dh.bmn.entity.CategoriaBicicleta;
import com.dh.bmn.repository.ICategoriaBicicletaRepository;
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
public class CategoriaBicicletaService implements IService<CategoriaBicicleta, CategoriaBicicletaDto>{

    @Autowired
    private final ICategoriaBicicletaRepository categoriaBicicletaRepository;
    @Autowired
    private final ObjectMapper mapper;

    public CategoriaBicicletaService(ICategoriaBicicletaRepository categoriaBicicletaRepository, ObjectMapper mapper) {
        this.categoriaBicicletaRepository = categoriaBicicletaRepository;
        this.mapper = new ObjectMapper();
    }

    private static final Logger LOGGER = LogManager.getLogger(BicicletaService.class);

    @Override
    public void actualizar(CategoriaBicicleta categoriaBicicleta) throws Exception {

    }

    @Override
    public Optional<CategoriaBicicletaDto> buscarPorId(Integer id) throws Exception {
        return Optional.empty();
    }

    @Override
    public void guardar(CategoriaBicicleta nuevaCategoriaBicicleta) throws Exception {
        String inicialNombre = nuevaCategoriaBicicleta.getNombre().substring(0, 1);
        String restoNombre = nuevaCategoriaBicicleta.getNombre().substring(1);
        nuevaCategoriaBicicleta.setNombre(inicialNombre.toUpperCase() + restoNombre.toLowerCase());

        categoriaBicicletaRepository.save(nuevaCategoriaBicicleta);
        //LOGGER.info("Se creó una nueva bicicleta: " + nuevoBicicleta.toString());
    }

    @Override
    public void borrarPorId(Integer id) throws Exception {
        Optional<CategoriaBicicleta> optionalCategoriaBicicleta = categoriaBicicletaRepository.findById(id);
        if (optionalCategoriaBicicleta.isPresent()) {
            categoriaBicicletaRepository.deleteById(id);
            //LOGGER.info("Se eliminó la bicicleta con el ID: " + id);
        } else {
            throw new RuntimeException();
            //throw new NotFoundException("Código 201", "No se encontró la bicicleta con el ID: " + id);
        }
    }

    @Override
    public Set<CategoriaBicicletaDto> listarTodos() throws Exception {
        List<CategoriaBicicleta> categoriasBicicletas = categoriaBicicletaRepository.findAll();
        return categoriasBicicletas.stream().map(cb-> mapper.convertValue(cb, CategoriaBicicletaDto.class)).collect(Collectors.toSet());
    }
}
