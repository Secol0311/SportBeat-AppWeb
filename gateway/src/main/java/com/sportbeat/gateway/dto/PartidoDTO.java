package com.sportbeat.gateway.dto;
import com.sportbeat.gateway.model.EstadoPartido;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
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
}