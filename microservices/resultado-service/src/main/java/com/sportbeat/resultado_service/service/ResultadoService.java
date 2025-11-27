package com.sportbeat.resultado_service.service;

import com.sportbeat.resultado_service.dto.CrearResultadoRequest;
import com.sportbeat.resultado_service.model.Resultado;
import com.sportbeat.resultado_service.repository.ResultadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class ResultadoService {

    @Autowired
    private ResultadoRepository resultadoRepository;

    public Resultado crearResultado(CrearResultadoRequest request) {
        if (resultadoRepository.existsByPartidoId(request.getPartidoId())) {
            throw new IllegalStateException("Ya existe un resultado para este partido.");
        }
        Resultado nuevoResultado = Resultado.builder()
                .partidoId(request.getPartidoId())
                .golesLocal(request.getGolesLocal())
                .golesVisitante(request.getGolesVisitante())
                .registradoPorUsuarioId(request.getRegistradoPorUsuarioId())
                .build();
        return resultadoRepository.save(nuevoResultado);
    }

    public Resultado obtenerResultadoPorPartidoId(UUID partidoId) {
        return resultadoRepository.findByPartidoId(partidoId)
                .orElseThrow(() -> new RuntimeException("Resultado no encontrado para el partido: " + partidoId));
    }
}