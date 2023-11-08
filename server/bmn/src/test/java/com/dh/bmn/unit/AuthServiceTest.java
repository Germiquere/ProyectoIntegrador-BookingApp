package com.dh.bmn.unit;

import com.dh.bmn.entity.Usuario;
import com.dh.bmn.exceptions.RequestValidationException;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.IUsuarioRepository;
import com.dh.bmn.security.auth.AuthResponseDto;
import com.dh.bmn.security.auth.AuthService;
import com.dh.bmn.security.auth.LoginRequestDto;
import com.dh.bmn.security.auth.RegisterRequestDto;
import com.dh.bmn.security.jwt.JwtService;
import com.dh.bmn.security.user.Rol;
import com.dh.bmn.services.impl.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private IUsuarioRepository usuarioRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AuthService authService;


    @BeforeEach
    public void setup() {
        authenticationManager = mock(AuthenticationManager.class);
        passwordEncoder = mock(PasswordEncoder.class);
        emailService = mock(EmailService.class);
        usuarioRepository = mock(IUsuarioRepository.class);
        jwtService = mock(JwtService.class);
        authService = new AuthService(usuarioRepository, jwtService, passwordEncoder, authenticationManager,emailService);
    }
    @Test
    public void testLogin() {

        LoginRequestDto request = new LoginRequestDto("juan.perez@gmail.com", "password");
        Usuario userDetails = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password576", Rol.USER);
        String token = "dummyToken";

        Mockito.when(jwtService.getToken(userDetails)).thenReturn(token);
        Mockito.when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(userDetails));

        AuthResponseDto response = authService.login(request);

        Assertions.assertEquals(token, response.getToken());
    }

    @Test
    public void testRegister() throws MessagingException {

        RegisterRequestDto request = new RegisterRequestDto("Juan", "Perez", "juan.perez@gmail.com", "Password123");

        Mockito.when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword");
        Mockito.when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        Assertions.assertDoesNotThrow(() -> authService.register(request));

        Mockito.verify(emailService).sendWelcomeEmail(request.getEmail(), request.getNombre());
    }

    @Test
    public void testRegisterThrowsResourceAlreadyExistsException() {

        RegisterRequestDto request = new RegisterRequestDto("Juan", "Perez", "juan.perez@gmail.com", "Password123");

        Mockito.when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new Usuario()));

        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> authService.register(request));
    }

    @Test
    public void testRegisterValidarMailThrowsRequestValidationException() {

        RegisterRequestDto request = new RegisterRequestDto("Juan", "Perez", "juan.perez$@gmail.com", "Password123");

        Assertions.assertThrows(RequestValidationException.class, () -> authService.register(request));
    }

    @Test
    public void testRegisterValidarPasswordThrowsRequestValidationException() {

        RegisterRequestDto request = new RegisterRequestDto("Juan", "Perez", "juan.perez@gmail.com", "password123");

        Assertions.assertThrows(RequestValidationException.class, () -> authService.register(request));
    }

    @Test
    public void testReenviarCorreoConfirmacion() throws MessagingException {

        String email = "juan.perez@gmail.com";
        Usuario usuario = new Usuario(1L, "Juan", "Perez", "juan.perez@gmail.com", "password576", Rol.USER);

        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        authService.reenviarCorreoConfirmacion(email);

        Mockito.verify(emailService).sendWelcomeEmail(eq(email), eq("Juan"));
    }

    @Test
    public void testReenviarCorreoConfirmacionThrowsResourceNotFoundException() {

        String email = "juanPerez@gmail.com";

        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> authService.reenviarCorreoConfirmacion(email));
    }
}







