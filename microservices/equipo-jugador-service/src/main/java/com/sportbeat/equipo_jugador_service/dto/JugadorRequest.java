package com.sportbeat.equipo_jugador_service.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JugadorRequest {

    private UUID id; // Para actualizaciones

    @NotNull(message = "El usuario es obligatorio")
    private UUID usuarioId; // FK hacia Usuario (rol PLAYER)

    private UUID teamId; // Puede estar vacío si no tiene equipo

    @PositiveOrZero(message = "El número de camiseta debe ser positivo o cero")
    private Integer jerseyNumber;

    @Size(max = 30)
    private String position;

    @PositiveOrZero(message = "La altura debe ser positiva o cero")
    private Integer heightCm;

    @PositiveOrZero(message = "El peso debe ser positivo o cero")
    private Integer weightKg;
}
