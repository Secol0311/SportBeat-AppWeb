package com.sportbeat.resultado_service.controller;

import com.sportbeat.resultado_service.dto.CrearResultadoRequest;
import com.sportbeat.resultado_service.model.Resultado;
import com.sportbeat.resultado_service.service.ResultadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/resultados")
public class ResultadoController {

    @Autowired
    private ResultadoService resultadoService;

    @PostMapping
    public ResponseEntity<Resultado> crearResultado(@Valid @RequestBody CrearResultadoRequest request) {
        Resultado nuevoResultado = resultadoService.crearResultado(request);
        return new ResponseEntity<>(nuevoResultado, HttpStatus.CREATED);
    }

    @GetMapping("/partido/{partidoId}")
    public ResponseEntity<Resultado> obtenerResultadoPorPartido(@PathVariable UUID partidoId) {
        try {
            Resultado resultado = resultadoService.obtenerResultadoPorPartidoId(partidoId);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}