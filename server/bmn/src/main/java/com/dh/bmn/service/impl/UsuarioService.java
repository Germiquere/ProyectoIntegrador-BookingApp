package com.dh.bmn.service.impl;

import com.dh.bmn.dto.requests.UsuarioRequestDto;
<<<<<<< HEAD
import com.dh.bmn.dto.responses.UsuarioResponseDto;
=======
>>>>>>> 9c9bbdd5138d4d98b3958af2ff7ccbebc0f7e8f6
import com.dh.bmn.entity.Usuario;
import com.dh.bmn.repository.IUsuarioRepository;
import com.dh.bmn.service.IService;
import com.dh.bmn.util.MapperClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
<<<<<<< HEAD
public class UsuarioService implements IService<UsuarioResponseDto, UsuarioRequestDto> {
=======
public class UsuarioService implements IService<Usuario, UsuarioRequestDto> {
>>>>>>> 9c9bbdd5138d4d98b3958af2ff7ccbebc0f7e8f6

    @Autowired
    private final IUsuarioRepository usuarioRepository;

    private static final ObjectMapper objectMapper = MapperClass.objectMapper();

    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    private static final Logger LOGGER = LogManager.getLogger(UsuarioService.class);

    @Override
    public void actualizar(UsuarioRequestDto usuarioRequestDto) throws Exception {

    }

    @Override
<<<<<<< HEAD
    public Optional<UsuarioResponseDto> buscarPorId(Long id) throws Exception {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isPresent()) {
            return usuario.stream().map(u-> objectMapper.convertValue(u, UsuarioResponseDto.class)).findFirst();
=======
    public Optional<UsuarioRequestDto> buscarPorId(Integer id) throws Exception {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if(usuario.isPresent()) {
            return usuario.stream().map(u-> mapper.convertValue(u, UsuarioRequestDto.class)).findFirst();
>>>>>>> 9c9bbdd5138d4d98b3958af2ff7ccbebc0f7e8f6
        } else {
            throw new RuntimeException();
            //throw new NotFoundException("Código 201", "No se encontró el paciente con el ID: " + id);
        }
    }

    @Override
    public void guardar(UsuarioRequestDto usuarioRequestDto) throws Exception {
        String inicialNombre = usuarioRequestDto.getNombre().substring(0, 1);
        String restoNombre = usuarioRequestDto.getNombre().substring(1);
        usuarioRequestDto.setNombre(inicialNombre.toUpperCase() + restoNombre.toLowerCase());

        String inicialApellido = usuarioRequestDto.getApellido().substring(0, 1);
        String restoApellido = usuarioRequestDto.getApellido().substring(1);
        usuarioRequestDto.setApellido(inicialApellido.toUpperCase() + restoApellido.toLowerCase());

        Usuario usuario = objectMapper.convertValue(usuarioRequestDto, Usuario.class);
        usuarioRepository.save(usuario);
        LOGGER.info("Se creó un nuevo usuario: " + usuario.toString());
    }

    @Override
    public void borrarPorId(Long id) throws Exception {
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
<<<<<<< HEAD
    public List<UsuarioResponseDto> listarTodas() throws Exception {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(u -> objectMapper.convertValue(u, UsuarioResponseDto.class)).collect(Collectors.toList());
=======
    public Set<UsuarioRequestDto> listarTodos() throws Exception {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream().map(u-> mapper.convertValue(u, UsuarioRequestDto.class)).collect(Collectors.toSet());
>>>>>>> 9c9bbdd5138d4d98b3958af2ff7ccbebc0f7e8f6
    }
}
