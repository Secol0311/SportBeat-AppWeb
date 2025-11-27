package com.sportbeat.equipo_jugador_service.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LigaRequest {

    private UUID id; // Para actualizaciones

    @NotBlank(message = "El código de la liga es obligatorio")
    private String leagueCode;

    @NotBlank(message = "El nombre de la liga es obligatorio")
    private String name;

    @NotBlank(message = "El deporte es obligatorio")
    private String sport;

    @NotBlank(message = "La temporada es obligatoria")
    private String season;

    private UUID adminId; // FK hacia el administrador
}
