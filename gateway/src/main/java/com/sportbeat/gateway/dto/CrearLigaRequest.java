package com.sportbeat.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO para la creación o actualización de una Liga.
 * Se usa en los formularios de Thymeleaf del dashboard de administrador.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearLigaRequest {
    private UUID id; // Se usa para la edición, es null en la creación
    private String leagueCode;
    private String name;
    private String sport;
    private String season;
}