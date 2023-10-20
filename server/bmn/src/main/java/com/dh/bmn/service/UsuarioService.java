package com.dh.bmn.service;

import com.dh.bmn.dto.UsuarioDto;
import com.dh.bmn.entity.Usuario;
import com.dh.bmn.repository.IUsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UsuarioService implements IService<Usuario, UsuarioDto>{

    @Autowired
    private final IUsuarioRepository usuarioRepository;
    @Autowired
    private final ObjectMapper mapper;

    private static final Logger LOGGER = LogManager.getLogger(UsuarioService.class);

    @Override
    public void actualizar(Usuario usuario) throws Exception {

    }

    @Override
    public Optional<UsuarioDto> buscarPorId(Integer id) throws Exception {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isPresent()) {
            return usuario.stream().map(u-> mapper.convertValue(u, UsuarioDto.class)).findFirst();
        } else {
            throw new RuntimeException();
            //throw new NotFoundException("Código 201", "No se encontró el paciente con el ID: " + id);
        }
    }

    @Override
    public void guardar(Usuario nuevoUsuario) throws Exception {
        String inicialNombre = nuevoUsuario.getNombre().substring(0, 1);
        String restoNombre = nuevoUsuario.getNombre().substring(1);
        nuevoUsuario.setNombre(inicialNombre.toUpperCase() + restoNombre.toLowerCase());

        String inicialApellido = nuevoUsuario.getApellido().substring(0, 1);
        String restoApellido = nuevoUsuario.getApellido().substring(1);
        nuevoUsuario.setApellido(inicialApellido.toUpperCase() + restoApellido.toLowerCase());

        usuarioRepository.save(nuevoUsuario);
        LOGGER.info("Se creó un nuevo usuario: " + nuevoUsuario.toString());
    }

    @Override
    public void borrarPorId(Integer id) throws Exception {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            usuarioRepository.deleteById(id);
            LOGGER.info("Se eliminó el usuario con el ID: " + id);
        } else {
            throw new RuntimeException();
            //throw new NotFoundException("Código 201", "No se encontró la bicicleta con el ID: " + id);
        }
    }

    @Override
    public Set<UsuarioDto> listarTodos() throws Exception {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(u-> mapper.convertValue(u, UsuarioDto.class)).collect(Collectors.toSet());
    }
}
