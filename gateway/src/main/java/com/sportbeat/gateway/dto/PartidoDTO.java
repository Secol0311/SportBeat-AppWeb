package com.sportbeat.gateway.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PartidoDTO {
    private Long id;
    private Long idLiga;
    private String nombreEquipoLocal;
    private String nombreEquipoVisitante;
    private LocalDateTime fechaHora;
    private String lugar;
    private Integer golesLocal;
    private Integer golesVisitante;
    private String estado; // ej. "PROGRAMADO", "JUGADO", "CANCELADO"
}