package com.dh.bmn.unit;

import com.dh.bmn.dtos.requests.UsuarioRequestDto;
import com.dh.bmn.dtos.responses.UsuarioResponseDto;
import com.dh.bmn.entity.Usuario;
import com.dh.bmn.exceptions.RequestValidationException;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.IUsuarioRepository;
import com.dh.bmn.security.user.Rol;
import com.dh.bmn.services.impl.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setup() {

        usuarioRepository = mock(IUsuarioRepository.class);
        usuarioService = new UsuarioService(usuarioRepository);
    }

    @Test
    public void crearUsuario() {
        //Arrange
        UsuarioRequestDto usuarioRequestDto =
                new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
        //Act
        usuarioService.crear(usuarioRequestDto);
        // Asserts
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void crearUsuarioThrowsResourceAlreadyExistsException() {
        //Arrange
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
        Usuario usuarioEntity = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password576", Rol.USER);

        //Act
        when(usuarioRepository.findByEmail(usuarioRequestDto.getEmail())).thenReturn(Optional.of(usuarioEntity));

        // Asserts
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> {
            usuarioService.crear(usuarioRequestDto);
        });

    }

    @Test
    public void crearUsuarioValidarEmailThrowsRequestValidationException() {
        //Arrange
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perezgmail.com");

        // Asserts
        Assertions.assertThrows(RequestValidationException.class, () -> {
            usuarioService.crear(usuarioRequestDto);
        });

    }

//    @Test
//    public void crearUsuarioValidarPasswordThrowsRequestValidationException() {
//        //Arrange
//        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
//
//        // Asserts
//        Assertions.assertThrows(RequestValidationException.class, () -> {
//            usuarioService.crear(usuarioRequestDto);
//        });
//
//    }


    @Test
    public void obtenerUsuarioPorId() {
        //Arrange
        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password576", Rol.USER);
        UsuarioResponseDto usuarioEsperado = new UsuarioResponseDto(1L, "Juan", "Perez", "juan.perez@gmail.com", Rol.USER);

        //Act
        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        UsuarioResponseDto usuarioResponseDto = usuarioService.buscarPorId(1L);

        // Asserts
        Assertions.assertEquals(usuarioEsperado.getUsuarioId(), usuarioResponseDto.getUsuarioId());
    }

    @Test
    public void obtenerUsuarioPorIdThrowsResourceNotFoundException() {
        Mockito.when(usuarioRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.buscarPorId(1L);
        });
    }

    @Test
    public void obtenerTodosLosUsuarios() {
        //Arrange

        Usuario usuarioEntity = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password576", Rol.USER);
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuarioEntity);
        UsuarioResponseDto usuarioEsperado = new UsuarioResponseDto(1L, "Juan", "Perez", "juan.perez@gmail.com", Rol.USER);
        List<UsuarioResponseDto> usuarioResponseLista = new ArrayList<>();
        usuarioResponseLista.add(usuarioEsperado);

        //Act
        Mockito.when(usuarioRepository.findAll()).thenReturn(usuarios);
        List<UsuarioResponseDto> usuariosLista = usuarioService.listarTodos();

        // Asserts
        Assertions.assertEquals(usuarioResponseLista.size(), usuariosLista.size());
    }

    @Test
    public void listarTodosUsuariosThrowsResourceNotFoundException() {
        Mockito.when(usuarioRepository.findAll()).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.listarTodos();
        });
    }

    @Test
    public void eliminarUsuario() {
        //Arrange
        Usuario usuarioEntity = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password576", Rol.USER);

        //Act
        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));
        doNothing().when(usuarioRepository).delete(usuarioEntity);
        usuarioService.borrarPorId(1L);

        // Asserts
        verify(usuarioRepository, times(1)).delete(usuarioEntity);
    }

    @Test
    public void eliminarUsuarioThrowsResourceNotFoundException() {
        Mockito.when(usuarioRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.borrarPorId(1L);
        });
    }

    @Test
    public void actualizarUsuario() {
        //Arrange
        Usuario usuarioEntity = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password576", Rol.USER);
        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");

        //Act
        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));

        usuarioService.actualizar(usuarioRequestDto);

        // Asserts
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void actualizarUsuarioThrowsResourceNotFoundException() {

        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
        Mockito.when(usuarioRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.actualizar(usuarioRequestDto);
        });
    }

    @Test
    public void actualizarUsuarioValidarEmailThrowsRequestValidationException() {

        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perezgmail.com");
        Usuario usuarioEntity = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password576", Rol.USER);
        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));

        Assertions.assertThrows(RequestValidationException.class, () -> {
            usuarioService.actualizar(usuarioRequestDto);
        });
    }

//    @Test
//    public void actualizarUsuarioValidarPasswordThrowsRequestValidationException() {
//
//        UsuarioRequestDto usuarioRequestDto = new UsuarioRequestDto(1L, "Juan", "Perez", "juan.perez@gmail.com");
//        Usuario usuarioEntity = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password576", Rol.USER);
//        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioEntity));
//
//        Assertions.assertThrows(RequestValidationException.class, () -> {
//            usuarioService.actualizar(usuarioRequestDto);
//        });
//    }

}
