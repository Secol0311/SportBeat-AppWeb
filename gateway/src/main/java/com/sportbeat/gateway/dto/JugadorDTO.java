package com.sportbeat.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para representar un Jugador, consumido desde equipo-jugador-service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JugadorDTO {
    private UUID id;
    private UUID usuarioId; // ID del usuario que es un jugador
    private UUID teamId; // ID del equipo al que pertenece (puede ser null)
    private Integer jerseyNumber;
    private String position;
    private Integer heightCm;
    private Integer weightKg;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}