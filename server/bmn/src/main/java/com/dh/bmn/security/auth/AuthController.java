package com.dh.bmn.security.auth;

import com.dh.bmn.dtos.JsonMessageDto;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "/registrar")
    public ResponseEntity<?> registrar(@RequestBody @Valid RegisterRequestDto request) throws MessagingException {
        authService.register(request);
        return new ResponseEntity<>(new JsonMessageDto("Nuevo usuario registrado", HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @PostMapping(value = "/reenviar-email")
    public ResponseEntity<?> reenviarCorreoConfirmacion(@RequestParam String email) throws MessagingException {
        authService.reenviarCorreoConfirmacion(email);
        return new ResponseEntity<>(new JsonMessageDto("Correo de confirmación reenviado", HttpStatus.OK.value()), HttpStatus.OK);
    }

}
