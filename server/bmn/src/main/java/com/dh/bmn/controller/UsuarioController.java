package com.dh.bmn.controller;

import com.dh.bmn.dto.requests.UsuarioRequestDto;
import com.dh.bmn.entity.Usuario;
import com.dh.bmn.service.impl.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Optional<UsuarioRequestDto> obtenerUsuarioPorId (@PathVariable Integer id) throws Exception{
        return usuarioService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<?> registrarUsuario (@RequestBody Usuario nuevoUsuario) throws Exception {
        usuarioService.guardar(nuevoUsuario);
        return ResponseEntity.ok("Nuevo usuario registrado.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuarioPorId(@PathVariable Integer id) throws Exception {
        usuarioService.borrarPorId(id);
        return ResponseEntity.ok("Usuario eliminado.");
    }

    @GetMapping
    public Set<UsuarioRequestDto> listaDeUsuarios () throws Exception {
        return usuarioService.listarTodos();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@RequestBody Usuario usuarioActualizado) throws Exception {
        usuarioService.actualizar(usuarioActualizado);
        return ResponseEntity.ok("Usuario actualizado.");
    }

}
