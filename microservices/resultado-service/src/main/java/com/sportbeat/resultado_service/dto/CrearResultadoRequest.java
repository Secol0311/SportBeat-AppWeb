package com.sportbeat.resultado_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class CrearResultadoRequest {
    @NotNull(message = "El ID del partido es obligatorio")
    private UUID partidoId;

    @Min(value = 0, message = "Los goles no pueden ser negativos")
    private Integer golesLocal = 0;

    @Min(value = 0, message = "Los goles no pueden ser negativos")
    private Integer golesVisitante = 0;

    private UUID registradoPorUsuarioId; // Opcional, se puede obtener del token JWT
}