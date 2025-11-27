package com.sportbeat.equipo_jugador_service.controller;

import com.sportbeat.equipo_jugador_service.model.Usuario;
import com.sportbeat.equipo_jugador_service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/public") // Ruta pública para que otros servicios puedan llamar
public class PublicUserController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios/{username}")
    public ResponseEntity<Usuario> getUsuarioByUsername(@PathVariable String username) {
        // Importante: No devolver la contraseña en un endpoint real.
        // Pero para la validación interna de auth-service, es necesario.
        return usuarioRepository.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}