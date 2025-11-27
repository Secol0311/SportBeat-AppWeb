package com.sportbeat.equipo_jugador_service.controller;

import com.sportbeat.equipo_jugador_service.dto.JugadorRequest;
import com.sportbeat.equipo_jugador_service.model.Jugador;
import com.sportbeat.equipo_jugador_service.service.JugadorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    private final JugadorService jugadorService;

    public JugadorController(JugadorService jugadorService) {
        this.jugadorService = jugadorService;
    }

    @PostMapping
    public ResponseEntity<Jugador> crearJugador(@Valid @RequestBody JugadorRequest request) {
        Jugador jugador = jugadorService.crearJugador(request);
        return ResponseEntity.status(201).body(jugador);
    }

    @GetMapping
    public ResponseEntity<List<Jugador>> obtenerTodos() {
        return ResponseEntity.ok(jugadorService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jugador> obtenerPorId(@PathVariable UUID id) {
        Jugador jugador = jugadorService.obtenerPorId(id);
        if (jugador == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jugador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jugador> actualizarJugador(
            @PathVariable UUID id,
            @Valid @RequestBody JugadorRequest request
    ) {
        Jugador jugadorActualizado = jugadorService.actualizarJugador(id, request);
        if (jugadorActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jugadorActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJugador(@PathVariable UUID id) {
        jugadorService.eliminarJugador(id);
        return ResponseEntity.noContent().build();
    }
}
