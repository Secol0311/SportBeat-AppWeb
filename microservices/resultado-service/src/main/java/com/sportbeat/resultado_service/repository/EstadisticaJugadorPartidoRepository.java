package com.sportbeat.resultado_service.repository;

import com.sportbeat.resultado_service.model.EstadisticaJugadorPartido;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface EstadisticaJugadorPartidoRepository extends JpaRepository<EstadisticaJugadorPartido, UUID> {
    List<EstadisticaJugadorPartido> findByPartidoId(UUID partidoId);
    List<EstadisticaJugadorPartido> findByJugadorId(UUID jugadorId);
}