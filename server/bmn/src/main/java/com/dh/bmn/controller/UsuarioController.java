package com.dh.bmn.controller;

import com.dh.bmn.dto.requests.UsuarioRequestDto;
<<<<<<< HEAD
import com.dh.bmn.dto.responses.UsuarioResponseDto;
import com.dh.bmn.service.IService;
=======
import com.dh.bmn.entity.Usuario;
import com.dh.bmn.service.impl.UsuarioService;
>>>>>>> 9c9bbdd5138d4d98b3958af2ff7ccbebc0f7e8f6
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bike-me-now/usuarios")
public class UsuarioController {

    private final IService<UsuarioResponseDto, UsuarioRequestDto> usuarioService;

    @Autowired
    public UsuarioController(IService<UsuarioResponseDto, UsuarioRequestDto> usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
<<<<<<< HEAD
    public ResponseEntity<Optional<UsuarioResponseDto>> obtenerUsuarioPorId (@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(usuarioService.buscarPorId(id), HttpStatus.OK);
=======
    public Optional<UsuarioRequestDto> obtenerUsuarioPorId (@PathVariable Integer id) throws Exception{
        return usuarioService.buscarPorId(id);
>>>>>>> 9c9bbdd5138d4d98b3958af2ff7ccbebc0f7e8f6
    }

    @PostMapping
    public ResponseEntity<?> registrarUsuario (@RequestBody UsuarioRequestDto usuarioRequestDto) throws Exception {
        usuarioService.guardar(usuarioRequestDto);
        return new ResponseEntity<>("Nuevo usuario registrado", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuarioPorId (@PathVariable Long id) throws Exception {
        usuarioService.borrarPorId(id);
        return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.OK);
    }

    @GetMapping
<<<<<<< HEAD
    public ResponseEntity<List<UsuarioResponseDto>> listarUsuarios () throws Exception {
        return new ResponseEntity<>(usuarioService.listarTodas(),HttpStatus.OK);
=======
    public Set<UsuarioRequestDto> listaDeUsuarios () throws Exception {
        return usuarioService.listarTodos();
>>>>>>> 9c9bbdd5138d4d98b3958af2ff7ccbebc0f7e8f6
    }

    @PutMapping()
    public ResponseEntity<?> actualizarUsuario (@RequestBody UsuarioRequestDto usuarioRequestDto) throws Exception {
        usuarioService.actualizar(usuarioRequestDto);
        return new ResponseEntity<>("Usuario actualizado exitosamente", HttpStatus.OK);
    }
}
