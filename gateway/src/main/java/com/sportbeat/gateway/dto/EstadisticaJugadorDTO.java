package com.sportbeat.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para representar las estadísticas de un jugador en un partido,
 * consumido desde resultados-service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadisticaJugadorDTO {
    private UUID id;
    private UUID partidoId;
    private UUID jugadorId;
    private Integer goles;
    private Integer asistencias;
    private Integer tarjetasAmarillas;
    private Integer tarjetasRojas;
    private Integer minutosJugados;
    private LocalDateTime createdAt;
}