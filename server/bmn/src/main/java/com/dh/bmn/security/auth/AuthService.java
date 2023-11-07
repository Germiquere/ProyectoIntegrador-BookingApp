package com.dh.bmn.security.auth;

import com.dh.bmn.entity.Usuario;
import com.dh.bmn.exceptions.RequestValidationException;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.exceptions.ResourceNotFoundException;
import com.dh.bmn.repositories.IUsuarioRepository;
import com.dh.bmn.security.jwt.JwtService;
import com.dh.bmn.security.user.Rol;
import com.dh.bmn.services.impl.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails usuario = usuarioRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(usuario);
        return AuthResponseDto.builder()
                .token(token)
                .build();
    }

    public AuthResponseDto register(RegisterRequestDto request) throws MessagingException {

        validarEmail(request.getEmail());
        validarPassword(request.getPassword());

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(Rol.USER)
                .build();

        normalizarNombreApellido(request);

        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("El usuario ya existe", HttpStatus.CONFLICT.value());
        } else {
            usuarioRepository.save(usuario);

            emailService.sendWelcomeEmail(request.getEmail(), request.getNombre());

            return AuthResponseDto.builder().build();
            //.token(jwtService.getToken(usuario))
            //.build();
        }

    }

    private void normalizarNombreApellido(RegisterRequestDto request) {

        String inicialNombre = request.getNombre().substring(0, 1);
        String restoNombre = request.getNombre().substring(1);
        request.setNombre(inicialNombre.toUpperCase() + restoNombre.toLowerCase());

        String inicialApellido = request.getApellido().substring(0, 1);
        String restoApellido = request.getApellido().substring(1);
        request.setApellido(inicialApellido.toUpperCase() + restoApellido.toLowerCase());

    }

    public void reenviarCorreoConfirmacion(String email) throws MessagingException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado", HttpStatus.NOT_FOUND.value()));

        // Genera un nuevo token (si lo utilizas) y envía un correo de confirmación
        //String nuevoToken = jwtService.generarNuevoToken(usuario);
        emailService.sendWelcomeEmail(email, usuario.getNombre());
    }

    public static Boolean validarEmail(String email) {
        if ((email.contains("@")) && (email.length() > 10 && email.length() < 30)) {
            return true;
        } else {
            throw new RequestValidationException("El email tiene que tener entre 10 y 30 caracteres y contener un @", HttpStatus.BAD_REQUEST.value());
        }
    }

    public static Boolean validarPassword(String password) {
        String regex = "^[a-z0-9]{8,12}[^s][^$%&|<>#]$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(password);

        if (matcher.matches()) {
            return true;
        } else {
            throw new RequestValidationException("La contraseña no cumple con los valores especificados ", HttpStatus.BAD_REQUEST.value());
        }
    }
}
