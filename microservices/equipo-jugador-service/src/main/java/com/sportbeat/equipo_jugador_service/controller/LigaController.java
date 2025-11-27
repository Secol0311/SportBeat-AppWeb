package com.sportbeat.equipo_jugador_service.controller;

import com.sportbeat.equipo_jugador_service.dto.LigaRequest;
import com.sportbeat.equipo_jugador_service.model.Liga;
import com.sportbeat.equipo_jugador_service.service.LigaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ligas")
public class LigaController {

    @Autowired
    private LigaService ligaService;

    @PostMapping
    public ResponseEntity<Liga> crearLiga(@Valid @RequestBody LigaRequest request) {
        return new ResponseEntity<>(ligaService.crearLiga(request), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Liga> obtenerTodas() {
        return ligaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Liga> obtenerPorId(@PathVariable UUID id) {
        Liga liga = ligaService.obtenerPorId(id);
        return liga != null ? ResponseEntity.ok(liga) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Liga> actualizarLiga(@PathVariable UUID id, @Valid @RequestBody LigaRequest request) {
        try {
            return ResponseEntity.ok(ligaService.actualizarLiga(id, request));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLiga(@PathVariable UUID id) {
        ligaService.eliminarLiga(id);
        return ResponseEntity.noContent().build();
    }
}
