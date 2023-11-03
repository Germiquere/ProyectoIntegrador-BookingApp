package com.dh.bmn.services.impl;

import com.dh.bmn.dtos.requests.UsuarioRequestDto;
import com.dh.bmn.dtos.responses.UsuarioResponseDto;
import com.dh.bmn.entity.Usuario;
import com.dh.bmn.exceptions.RequestValidationException;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.IUsuarioRepository;
import com.dh.bmn.security.user.Rol;
import com.dh.bmn.services.IService;
import com.dh.bmn.util.MapperClass;
import com.dh.bmn.pagging.PaginatedResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UsuarioService implements IService<UsuarioResponseDto, UsuarioRequestDto> {

    private final IUsuarioRepository usuarioRepository;

    private PasswordEncoder passwordEncoder;

    private static final ObjectMapper objectMapper = MapperClass.objectMapper();


    @Override
    public void actualizar(UsuarioRequestDto usuarioRequestDto) {

        Usuario usuarioDB = usuarioRepository.findById(usuarioRequestDto.getUsuarioId()).orElseThrow(() -> new ResourceNotFoundException("El usuario no existe", HttpStatus.NOT_FOUND.value()));

        if (usuarioDB != null) {
            normalizarNombreApellido(usuarioRequestDto);
            usuarioDB = objectMapper.convertValue(usuarioRequestDto, Usuario.class);


            String nuevaPassword = usuarioRequestDto.getPassword();
            if (nuevaPassword != null && !nuevaPassword.isEmpty()) {
                String passwordEncriptada = passwordEncoder.encode(nuevaPassword);
                usuarioDB.setPassword(passwordEncriptada);
            }

            usuarioRepository.save(usuarioDB);
        }
    }

        @Override
        public UsuarioResponseDto buscarPorId (Long id){
            Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El usuario no existe", HttpStatus.NOT_FOUND.value()));
            return objectMapper.convertValue(usuario, UsuarioResponseDto.class);
        }

        @Override
        public void crear (UsuarioRequestDto usuarioRequestDto){
            normalizarNombreApellido(usuarioRequestDto);

            if (usuarioRepository.findByEmail(usuarioRequestDto.getEmail()).isPresent()) {
                throw new ResourceAlreadyExistsException("El usuario ya existe", HttpStatus.CONFLICT.value());
            }

            validarRol(usuarioRequestDto.getRol());
            Usuario usuario = objectMapper.convertValue(usuarioRequestDto, Usuario.class);
            String passwordEncriptada = passwordEncoder.encode(usuarioRequestDto.getPassword());
            usuario.setPassword(passwordEncriptada);

            usuarioRepository.save(usuario);
        }

        @Override
        public void borrarPorId (Long id){
            Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El usuario no existe", HttpStatus.NOT_FOUND.value()));
            usuarioRepository.delete(usuario);
        }

        @Override
        public List<UsuarioResponseDto> listarTodos () {
            List<Usuario> listaUsuarios = Optional.of(usuarioRepository.findAll()).orElseThrow(() -> new ResourceNotFoundException("No se encontraron usuarios", HttpStatus.NOT_FOUND.value()));

            return listaUsuarios
                    .stream()
                    .map(usuario -> objectMapper.convertValue(usuario, UsuarioResponseDto.class))
                    .collect(Collectors.toList());
        }

        private void validarRol (Rol rol){

            if (!rol.getValor().equals("USER") && !rol.getValor().equals("ADMIN")) {
                throw new RequestValidationException("El rol especificado no existe", HttpStatus.BAD_REQUEST.value());
            }
        }

        private void normalizarNombreApellido (UsuarioRequestDto usuarioRequestDto){

            String inicialNombre = usuarioRequestDto.getNombre().substring(0, 1);
            String restoNombre = usuarioRequestDto.getNombre().substring(1);
            usuarioRequestDto.setNombre(inicialNombre.toUpperCase() + restoNombre.toLowerCase());

            String inicialApellido = usuarioRequestDto.getApellido().substring(0, 1);
            String restoApellido = usuarioRequestDto.getApellido().substring(1);
            usuarioRequestDto.setApellido(inicialApellido.toUpperCase() + restoApellido.toLowerCase());

        }

        @Override
        public PaginatedResponse<UsuarioResponseDto> obtenerPaginacion ( int numeroPagina, int limit, int offset){
            return null;
        }
    }
