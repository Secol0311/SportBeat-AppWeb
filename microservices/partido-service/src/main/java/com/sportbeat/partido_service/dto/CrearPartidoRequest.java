package com.sportbeat.partido_service.dto;

import com.sportbeat.partido_service.model.EstadoPartido;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class CrearPartidoRequest {
    @NotNull(message = "El ID de la liga es obligatorio")
    private UUID ligaId;

    @NotNull(message = "El ID del equipo local es obligatorio")
    private UUID equipoLocalId;

    @NotNull(message = "El ID del equipo visitante es obligatorio")
    private UUID equipoVisitanteId;

    @NotNull(message = "La fecha del partido es obligatoria")
    @Future(message = "La fecha del partido debe ser en el futuro")
    private LocalDate fecha;

    @NotNull(message = "La hora del partido es obligatoria")
    private LocalTime hora;

    @NotBlank(message = "El lugar del partido no puede estar vacío")
    private String venue;
}