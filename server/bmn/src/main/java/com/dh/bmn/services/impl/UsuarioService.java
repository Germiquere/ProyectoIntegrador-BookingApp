package com.dh.bmn.services.impl;

import com.dh.bmn.dtos.requests.UsuarioRequestDto;
import com.dh.bmn.dtos.responses.UsuarioResponseDto;
import com.dh.bmn.entity.Usuario;
import com.dh.bmn.exceptions.RequestValidationException;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.IUsuarioRepository;
import com.dh.bmn.security.auth.AuthService;
import com.dh.bmn.security.user.Rol;
import com.dh.bmn.services.IService;
import com.dh.bmn.util.MapperClass;
import com.dh.bmn.pagging.PaginatedResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IService<UsuarioResponseDto, UsuarioRequestDto> {

    private final IUsuarioRepository usuarioRepository;

    private static final String secretKey = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    @Value("${jwt.secretKey}")
    private String secretKey;

    private static final ObjectMapper objectMapper = MapperClass.objectMapper();


    @Autowired
    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public void actualizar(UsuarioRequestDto usuarioRequestDto) {
        Usuario usuarioDB = usuarioRepository.findById(usuarioRequestDto.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe", HttpStatus.NOT_FOUND.value()));

        // Actualiza solo los campos específicos que se proporcionan en el DTO

        String nuevoNombre = usuarioRequestDto.getNombre();
        if (nuevoNombre != null) {
            usuarioDB.setNombre(nuevoNombre);
        }

        String nuevoApellido = usuarioRequestDto.getApellido();
        if (nuevoApellido != null) {
            usuarioDB.setApellido(nuevoApellido);
        }

        String nuevoMail = usuarioRequestDto.getEmail();
        if (nuevoMail != null) {
            AuthService.validarMail(nuevoMail);
            usuarioDB.setEmail(nuevoMail);
        }

        usuarioRepository.save(usuarioDB);
    }


    public void cambiarRol(Long usuarioId, Rol nuevoRol) {
        Usuario usuarioDB = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe", HttpStatus.NOT_FOUND.value()));

        if (nuevoRol == Rol.ADMIN || nuevoRol == Rol.USER) {
            usuarioDB.setRol(nuevoRol);
            usuarioRepository.save(usuarioDB);
        } else {
            throw new RequestValidationException("No tienes permisos para cambiar el rol de este usuario.", HttpStatus.BAD_REQUEST.value());
        }
    }

    @Override
    public UsuarioResponseDto buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El usuario no existe", HttpStatus.NOT_FOUND.value()));
        return objectMapper.convertValue(usuario, UsuarioResponseDto.class);
    }

    //TODO ---> Ver de eliminar metodo y crear una interfaz UsuarioService con todos los metodos menos el de crear y utlizar esa interfaz para el service en lugar de IService.
    @Override
    public void crear(UsuarioRequestDto usuarioRequestDto) {

        if (usuarioRepository.findByEmail(usuarioRequestDto.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("El usuario ya existe", HttpStatus.CONFLICT.value());
        }

        normalizarNombreApellido(usuarioRequestDto);
        AuthService.validarMail(usuarioRequestDto.getEmail());

        Usuario usuario = objectMapper.convertValue(usuarioRequestDto, Usuario.class);
//        String passwordEncriptada = passwordEncoder.encode(usuarioRequestDto.getPassword());
//        usuario.setPassword(passwordEncriptada);

        usuarioRepository.save(usuario);
    }

    @Override
    public void borrarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("El usuario no existe", HttpStatus.NOT_FOUND.value()));
        usuarioRepository.delete(usuario);
    }

    @Override
    public List<UsuarioResponseDto> listarTodos() {
        List<Usuario> listaUsuarios = Optional.of(usuarioRepository.findAll()).orElseThrow(() -> new ResourceNotFoundException("No se encontraron usuarios", HttpStatus.NOT_FOUND.value()));

        return listaUsuarios
                .stream()
                .map(usuario -> objectMapper.convertValue(usuario, UsuarioResponseDto.class))
                .collect(Collectors.toList());
    }

    private void normalizarNombreApellido(UsuarioRequestDto usuarioRequestDto) {

        String inicialNombre = usuarioRequestDto.getNombre().substring(0, 1);
        String restoNombre = usuarioRequestDto.getNombre().substring(1);
        usuarioRequestDto.setNombre(inicialNombre.toUpperCase() + restoNombre.toLowerCase());

        String inicialApellido = usuarioRequestDto.getApellido().substring(0, 1);
        String restoApellido = usuarioRequestDto.getApellido().substring(1);
        usuarioRequestDto.setApellido(inicialApellido.toUpperCase() + restoApellido.toLowerCase());

    }

    @Override
    public PaginatedResponse<UsuarioResponseDto> obtenerPaginacion(int numeroPagina, int limit, int offset) {
        return null;
    }


    //@Override
    public UsuarioResponseDto buscarUsuarioPorToken(String token) {

        // Decodificar el token JWT
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();

        // Extraer el correo electrónico del usuario desde las reclamaciones del token
        String email = claims.getSubject();

        // Buscar al usuario por correo electrónico en la base de datos
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("El usuario no existe", HttpStatus.NOT_FOUND.value()));

        return objectMapper.convertValue(usuario, UsuarioResponseDto.class);
    }

}
