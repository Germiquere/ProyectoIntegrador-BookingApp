package com.dh.bmn.security.auth;

import com.dh.bmn.dtos.requests.UsuarioRequestDto;
import com.dh.bmn.entity.Usuario;
import com.dh.bmn.exceptions.RequestValidationException;
import com.dh.bmn.exceptions.ResourceAlreadyExistsException;
import com.dh.bmn.repositories.IUsuarioRepository;
import com.dh.bmn.security.jwt.JwtService;
import com.dh.bmn.security.user.Rol;
import com.dh.bmn.services.impl.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails usuario = usuarioRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(usuario);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
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
        }

        usuarioRepository.save(usuario);

        emailService.sendWelcomeEmail(request.getEmail(), request.getNombre());

        return AuthResponse.builder()
                .token(jwtService.getToken(usuario))
                .build();
    }

    private void normalizarNombreApellido(RegisterRequest request) {

        String inicialNombre = request.getNombre().substring(0, 1);
        String restoNombre = request.getNombre().substring(1);
        request.setNombre(inicialNombre.toUpperCase() + restoNombre.toLowerCase());

        String inicialApellido = request.getApellido().substring(0, 1);
        String restoApellido = request.getApellido().substring(1);
        request.setApellido(inicialApellido.toUpperCase() + restoApellido.toLowerCase());

    }
}
