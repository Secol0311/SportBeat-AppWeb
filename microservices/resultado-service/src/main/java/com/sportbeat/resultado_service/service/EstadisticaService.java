package com.sportbeat.resultado_service.service;

import com.sportbeat.resultado_service.dto.CrearEstadisticaJugadorRequest;
import com.sportbeat.resultado_service.model.EstadisticaJugadorPartido;
import com.sportbeat.resultado_service.repository.EstadisticaJugadorPartidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class EstadisticaService {

    @Autowired
    private EstadisticaJugadorPartidoRepository estadisticaRepository;

    public List<EstadisticaJugadorPartido> registrarEstadisticas(List<CrearEstadisticaJugadorRequest> requests) {
        List<EstadisticaJugadorPartido> estadisticas = requests.stream()
                .map(this::mapToEntity)
                .toList();
        return estadisticaRepository.saveAll(estadisticas);
    }

    public List<EstadisticaJugadorPartido> obtenerEstadisticasPorPartido(UUID partidoId) {
        return estadisticaRepository.findByPartidoId(partidoId);
    }

    public List<EstadisticaJugadorPartido> obtenerEstadisticasPorJugador(UUID jugadorId) {
        return estadisticaRepository.findByJugadorId(jugadorId);
    }

    private EstadisticaJugadorPartido mapToEntity(CrearEstadisticaJugadorRequest request) {
        return EstadisticaJugadorPartido.builder()
                .partidoId(request.getPartidoId())
                .jugadorId(request.getJugadorId())
                .goles(request.getGoles())
                .asistencias(request.getAsistencias())
                .tarjetasAmarillas(request.getTarjetasAmarillas())
                .tarjetasRojas(request.getTarjetasRojas())
                .minutosJugados(request.getMinutosJugados())
                .build();
    }
}