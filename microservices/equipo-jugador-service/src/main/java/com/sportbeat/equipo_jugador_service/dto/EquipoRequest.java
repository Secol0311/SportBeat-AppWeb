package com.sportbeat.equipo_jugador_service.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EquipoRequest {

    private UUID id; // Para actualizaciones

    @NotBlank(message = "El nombre del equipo es obligatorio")
    private String name;
@NotBlank(message = "El distrito del equipo es obligatorio")
    private String district;
@NotBlank(message = "El departamento del equipo es obligatorio")
    private String department;

    private UUID leagueId; // FK hacia la liga

    private UUID coachId; // FK hacia el entrenador

    private Integer foundedYear; // Año de fundación
}
