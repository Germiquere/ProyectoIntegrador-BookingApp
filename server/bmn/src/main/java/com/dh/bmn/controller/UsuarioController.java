package com.dh.bmn.controller;

import com.dh.bmn.dto.UsuarioDto;
import com.dh.bmn.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public Optional<UsuarioDto> obtenerUsuarioPorId (@PathVariable Long id) throws Exception{
        return usuarioService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> registrarUsuario (@RequestBody UsuarioDto usuarioDto) throws Exception {
        usuarioService.guardar(usuarioDto);
        return ResponseEntity.ok("Usuario registrado.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuarioPorId(@PathVariable Long id) throws EntityNotFoundException {
        usuarioService.borrarPorId(id);
        return ResponseEntity.ok("Usuario eliminado.");
    }

    @GetMapping
    public Set<UsuarioDto> listaDeUsuarios () throws Exception {
        return usuarioService.listarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDto usuarioDto) {
        try {
            usuarioService.actualizar(id, usuarioDto);
            return ResponseEntity.ok("Usuario actualizado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el usuario: " + e.getMessage());
        }
    }

}
