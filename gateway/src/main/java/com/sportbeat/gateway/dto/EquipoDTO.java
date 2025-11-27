package com.sportbeat.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para representar un Equipo, consumido desde equipo-jugador-service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipoDTO {
    private UUID id;
    private String name;
    private String district;
    private String department;
    private UUID leagueId; // ID de la liga a la que pertenece
    private UUID coachId; // ID del usuario entrenador
    private Integer foundedYear;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}