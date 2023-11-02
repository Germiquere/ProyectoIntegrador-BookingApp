package com.dh.bmn.controllers;

import com.dh.bmn.dtos.JsonMessageDto;
import com.dh.bmn.dtos.requests.UsuarioRequestDto;
import com.dh.bmn.dtos.responses.UsuarioResponseDto;
import com.dh.bmn.services.IService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bike-me-now/api/usuarios")
public class UsuarioController {

    private final IService<UsuarioResponseDto, UsuarioRequestDto> usuarioService;

    @Autowired
    public UsuarioController(IService<UsuarioResponseDto, UsuarioRequestDto> usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    @Secured({ "ADMIN", "USER" })
    public ResponseEntity<UsuarioResponseDto> obtenerUsuarioPorId (@PathVariable Long id) {
        return new ResponseEntity<>(usuarioService.buscarPorId(id), HttpStatus.OK);
    }

    @PostMapping
    @Secured("ADMIN")
    public ResponseEntity<?> registrarUsuario (@RequestBody @Valid UsuarioRequestDto usuarioRequestDto){
        usuarioService.crear(usuarioRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Nuevo usuario registrado",HttpStatus.CREATED.value()), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Secured({ "ADMIN", "USER" })
    public ResponseEntity<?> eliminarUsuarioPorId (@PathVariable Long id){
        usuarioService.borrarPorId(id);
        return new ResponseEntity<>(new JsonMessageDto("Usuario eliminado exitosamente",HttpStatus.OK.value()), HttpStatus.OK);
    }

    @GetMapping
    @Secured("ADMIN")
    public ResponseEntity<List<UsuarioResponseDto>> listarUsuarios (){
        return new ResponseEntity<>(usuarioService.listarTodos(),HttpStatus.OK);
    }

    @PutMapping
    @Secured({ "ADMIN", "USER" })
    public ResponseEntity<?> actualizarUsuario (@RequestBody @Valid UsuarioRequestDto usuarioRequestDto){
        usuarioService.actualizar(usuarioRequestDto);
        return new ResponseEntity<>(new JsonMessageDto("Usuario actualizado exitosamente",HttpStatus.OK.value()), HttpStatus.OK);
    }

    @GetMapping("/buscar-por-token")
    @Secured({ "ADMIN", "USER" })
    public ResponseEntity<UsuarioResponseDto> obtenerUsuarioPorToken(@RequestHeader("Authorization") String token) {
        UsuarioResponseDto usuario = usuarioService.buscarPorToken(token);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

}
