package com.dh.bmn.service;

import com.dh.bmn.dto.UsuarioDto;
import com.dh.bmn.entity.Usuario;
import com.dh.bmn.repository.IUsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UsuarioService implements IUService <Usuario, UsuarioDto> {

    @Autowired
    private final IUsuarioRepository usuarioRepository;
    @Autowired
    private final ObjectMapper mapper;

    private static final Logger LOGGER = LogManager.getLogger(UsuarioService.class);

    @Override
    public void actualizar(Long id, UsuarioDto usuarioDto) throws Exception {
        Optional<Usuario> usuarioExistenteOptional = usuarioRepository.findById(id);

        if (usuarioExistenteOptional.isPresent()) {
            Usuario usuarioExistente = usuarioExistenteOptional.get();

            usuarioExistente.setNombre(usuarioDto.getNombre());
            usuarioExistente.setApellido(usuarioDto.getApellido());
            usuarioExistente.setEmail(usuarioDto.getEmail());
            usuarioExistente.setPassword(usuarioDto.getPassword());
            usuarioExistente.setBicicletas(usuarioDto.getBicicletas());

            usuarioRepository.save(usuarioExistente);
            //LOGGER.info("Nuevo usuario actualizado con éxito");
        } else {

            throw new RuntimeException("No se encontró el usuario con el ID: " + id);

            //LOGGER.info("No se encontró el usuario con el ID: " + id");
        }
    }

    @Override
    public Optional<UsuarioDto> buscarPorId(Long id) throws EntityNotFoundException {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
        return optionalUsuario.map(usuario -> mapper.convertValue(usuario, UsuarioDto.class));
        } else {
            throw new EntityNotFoundException("No se encontró un usuario con ese ID");
            //LOGGER.info("No se encontró el usuario con el ID: " + id");
        }
    }

    @Override
    public void guardar(UsuarioDto usuarioDto) throws Exception {
        Usuario usuario = mapper.convertValue(usuarioDto, Usuario.class);
        usuarioRepository.save(usuario);
        //LOGGER.info("Nuevo usuario guardado con éxito");
    }

    @Override
    public void borrarPorId(Long id) throws EntityNotFoundException {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            usuarioRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("No se encontró un usuario con ese ID");
            //LOGGER.info("No se encontró el usuario con el ID: " + id");
        }
    }

    @Override
    public Set<UsuarioDto> listarTodos() throws Exception {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(u-> mapper.convertValue(u, UsuarioDto.class)).collect(Collectors.toSet());
    }
}