package com.sportbeat.partido_service.dto;

import com.sportbeat.partido_service.model.EstadoPartido;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PartidoDTO {
    private UUID id;
    private UUID ligaId;
    private UUID equipoLocalId;
    private UUID equipoVisitanteId;
    private LocalDate fecha;
    private LocalTime hora;
    private String venue;
    private EstadoPartido estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}