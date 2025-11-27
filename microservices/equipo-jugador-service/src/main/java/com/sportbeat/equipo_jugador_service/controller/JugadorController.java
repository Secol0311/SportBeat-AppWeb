package com.sportbeat.equipo_jugador_service.controller;

import com.sportbeat.equipo_jugador_service.dto.JugadorRequest;
import com.sportbeat.equipo_jugador_service.model.Jugador;
import com.sportbeat.equipo_jugador_service.service.JugadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    @Autowired
    private JugadorService jugadorService;

    @PostMapping
    public ResponseEntity<Jugador> crearJugador(@Valid @RequestBody JugadorRequest request) {
        return new ResponseEntity<>(jugadorService.crearJugador(request), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Jugador> obtenerTodos() {
        return jugadorService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jugador> obtenerPorId(@PathVariable UUID id) {
        Jugador jugador = jugadorService.obtenerPorId(id);
        return jugador != null ? ResponseEntity.ok(jugador) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Jugador> actualizarJugador(@PathVariable UUID id, @Valid @RequestBody JugadorRequest request) {
        try {
            return ResponseEntity.ok(jugadorService.actualizarJugador(id, request));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarJugador(@PathVariable UUID id) {
        jugadorService.eliminarJugador(id);
        return ResponseEntity.noContent().build();
    }
}
