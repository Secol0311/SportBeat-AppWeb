package com.sportbeat.resultado_service.controller;

import com.sportbeat.resultado_service.dto.CrearEstadisticaJugadorRequest;
import com.sportbeat.resultado_service.model.EstadisticaJugadorPartido;
import com.sportbeat.resultado_service.service.EstadisticaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticaController {

    @Autowired
    private EstadisticaService estadisticaService;

    @PostMapping
    public ResponseEntity<List<EstadisticaJugadorPartido>> registrarEstadisticas(@Valid @RequestBody List<CrearEstadisticaJugadorRequest> requests) {
        List<EstadisticaJugadorPartido> estadisticasGuardadas = estadisticaService.registrarEstadisticas(requests);
        return new ResponseEntity<>(estadisticasGuardadas, HttpStatus.CREATED);
    }

    @GetMapping("/partido/{partidoId}")
    public ResponseEntity<List<EstadisticaJugadorPartido>> obtenerEstadisticasPorPartido(@PathVariable UUID partidoId) {
        List<EstadisticaJugadorPartido> estadisticas = estadisticaService.obtenerEstadisticasPorPartido(partidoId);
        return ResponseEntity.ok(estadisticas);
    }

    @GetMapping("/jugador/{jugadorId}")
    public ResponseEntity<List<EstadisticaJugadorPartido>> obtenerEstadisticasPorJugador(@PathVariable UUID jugadorId) {
        List<EstadisticaJugadorPartido> estadisticas = estadisticaService.obtenerEstadisticasPorJugador(jugadorId);
        return ResponseEntity.ok(estadisticas);
    }
}