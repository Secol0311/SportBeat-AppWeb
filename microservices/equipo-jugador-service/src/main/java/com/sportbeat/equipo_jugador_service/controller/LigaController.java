package com.sportbeat.equipo_jugador_service.controller;

import com.sportbeat.equipo_jugador_service.dto.LigaRequest;
import com.sportbeat.equipo_jugador_service.model.Liga;
import com.sportbeat.equipo_jugador_service.service.LigaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ligas")
public class LigaController {

    private final LigaService ligaService;

    public LigaController(LigaService ligaService) {
        this.ligaService = ligaService;
    }

    @PostMapping
    public ResponseEntity<Liga> crearLiga(@Valid @RequestBody LigaRequest request) {
        Liga liga = ligaService.crearLiga(request);
        return ResponseEntity.status(201).body(liga);
    }

    @GetMapping
    public ResponseEntity<List<Liga>> obtenerTodas() {
        return ResponseEntity.ok(ligaService.obtenerTodas());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countLigas() {
        return ResponseEntity.ok(ligaService.contarLigas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Liga> obtenerPorId(@PathVariable UUID id) {
        Liga liga = ligaService.obtenerPorId(id);
        return (liga != null) ? ResponseEntity.ok(liga) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Liga> actualizarLiga(
            @PathVariable UUID id,
            @Valid @RequestBody LigaRequest request
    ) {
        Liga ligaActualizada = ligaService.actualizarLiga(id, request);
        if (ligaActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ligaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLiga(@PathVariable UUID id) {
        ligaService.eliminarLiga(id);
        return ResponseEntity.noContent().build();
    }
}
