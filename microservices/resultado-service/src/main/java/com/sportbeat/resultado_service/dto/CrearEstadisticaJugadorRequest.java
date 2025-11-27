package com.sportbeat.resultado_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class CrearEstadisticaJugadorRequest {
    @NotNull(message = "El ID del partido es obligatorio")
    private UUID partidoId;

    @NotNull(message = "El ID del jugador es obligatorio")
    private UUID jugadorId;

    @Min(value = 0, message = "Los goles no pueden ser negativos")
    private Integer goles = 0;

    @Min(value = 0, message = "Las asistencias no pueden ser negativas")
    private Integer asistencias = 0;

    @Min(value = 0, message = "Las tarjetas amarillas no pueden ser negativas")
    private Integer tarjetasAmarillas = 0;

    @Min(value = 0, message = "Las tarjetas rojas no pueden ser negativas")
    private Integer tarjetasRojas = 0;

    private Integer minutosJugados;
}