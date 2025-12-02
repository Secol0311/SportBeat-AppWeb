package com.sportbeat.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * DTO para la creación o actualización de un Partido.
 * Se usa en los formularios de Thymeleaf del dashboard de administrador.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearPartidoRequest {
    private UUID id; // Se usa para la edición
    private UUID ligaId;
    private UUID equipoLocalId;
    private UUID equipoVisitanteId;
    private LocalDate fecha;
    private LocalTime hora;
    private String venue;
}