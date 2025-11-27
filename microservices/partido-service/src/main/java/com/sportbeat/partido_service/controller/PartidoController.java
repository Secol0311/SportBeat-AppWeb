package com.sportbeat.partido_service.controller;

import com.sportbeat.partido_service.dto.CrearPartidoRequest;
import com.sportbeat.partido_service.dto.PartidoDTO;
import com.sportbeat.partido_service.service.PartidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/partidos")
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    @PostMapping
    public ResponseEntity<PartidoDTO> crearPartido(@Valid @RequestBody CrearPartidoRequest request) {
        PartidoDTO nuevoPartido = partidoService.crearPartido(request);
        return new ResponseEntity<>(nuevoPartido, HttpStatus.CREATED);
    }

    @GetMapping("/calendario/liga/{ligaId}")
    public ResponseEntity<List<PartidoDTO>> obtenerCalendarioPorLiga(@PathVariable UUID ligaId) {
        List<PartidoDTO> calendario = partidoService.obtenerCalendarioPorLiga(ligaId);
        return ResponseEntity.ok(calendario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidoDTO> obtenerPartidoPorId(@PathVariable UUID id) {
        PartidoDTO partido = partidoService.obtenerPartidoPorId(id);
        if (partido != null) {
            return ResponseEntity.ok(partido);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPartido(@PathVariable UUID id) {
        try {
            partidoService.eliminarPartido(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("Partido-Service is running!");
    }
}