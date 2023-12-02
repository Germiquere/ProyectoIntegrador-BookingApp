package com.dh.bmn.security.auth;

import com.dh.bmn.dtos.JsonMessageDto;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
//    @RequestMapping(method = RequestMethod.OPTIONS, value = "/login" )
//    public ResponseEntity<?> handleOptionsRequest() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Access-Control-Allow-Origin", "http://bike-me-now-frontend.s3-website-us-east-1.amazonaws.com");
//        headers.add("Access-Control-Allow-Methods", "POST, OPTIONS");
//        headers.add("Access-Control-Allow-Headers", "Content-Type");
//        headers.add("Access-Control-Max-Age", "3600");
//
//        return ResponseEntity.ok().headers(headers).build();
//    }

}
