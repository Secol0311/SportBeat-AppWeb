package com.sportbeat.gateway.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ResultadoDTO {
    private UUID id;
    private UUID partidoId;
    private Integer golesLocal;
    private Integer golesVisitante;
    private UUID registradoPorUsuarioId;
    private LocalDateTime fechaRegistro;
}