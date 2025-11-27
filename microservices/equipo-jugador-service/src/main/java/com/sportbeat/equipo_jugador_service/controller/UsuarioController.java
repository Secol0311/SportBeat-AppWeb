package com.sportbeat.equipo_jugador_service.controller;

import com.sportbeat.equipo_jugador_service.dto.UsuarioRequest;
import com.sportbeat.equipo_jugador_service.model.Usuario;
import com.sportbeat.equipo_jugador_service.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody UsuarioRequest request) {
        return new ResponseEntity<>(usuarioService.crearUsuario(request), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Usuario> obtenerTodos() {
        return usuarioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable UUID id) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    @GetMapping("/public/{username}")
    public ResponseEntity<Usuario> obtenerPorUsername(@PathVariable String username) {
        Usuario usuario = usuarioService.findByUsername(username);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    @GetMapping("/count")
    public Long countUsuarios() {
        return usuarioService.contarUsuarios();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable UUID id, @Valid @RequestBody UsuarioRequest request) {
        try {
            return ResponseEntity.ok(usuarioService.actualizarUsuario(id, request));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable UUID id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}