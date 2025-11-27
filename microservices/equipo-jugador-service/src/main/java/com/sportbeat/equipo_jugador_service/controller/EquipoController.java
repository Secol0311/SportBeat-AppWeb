package com.sportbeat.equipo_jugador_service.controller;

import com.sportbeat.equipo_jugador_service.dto.EquipoRequest;
import com.sportbeat.equipo_jugador_service.model.Equipo;
import com.sportbeat.equipo_jugador_service.service.EquipoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @PostMapping
    public ResponseEntity<Equipo> crearEquipo(@Valid @RequestBody EquipoRequest request) {
        return new ResponseEntity<>(equipoService.crearEquipo(request), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Equipo> obtenerTodos() {
        return equipoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> obtenerPorId(@PathVariable UUID id) {
        Equipo equipo = equipoService.obtenerPorId(id);
        return equipo != null ? ResponseEntity.ok(equipo) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipo> actualizarEquipo(@PathVariable UUID id, @Valid @RequestBody EquipoRequest request) {
        try {
            return ResponseEntity.ok(equipoService.actualizarEquipo(id, request));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEquipo(@PathVariable UUID id) {
        equipoService.eliminarEquipo(id);
        return ResponseEntity.noContent().build();
    }
}
