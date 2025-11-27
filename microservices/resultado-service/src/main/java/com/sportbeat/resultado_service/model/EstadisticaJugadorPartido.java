package com.sportbeat.resultado_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "estadisticas_jugador_partido")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadisticaJugadorPartido {

    @Id
    private UUID id;

    @Column(name = "partido_id", nullable = false)
    private UUID partidoId;

    @Column(name = "jugador_id", nullable = false)
    private UUID jugadorId;

    private Integer goles;
    private Integer asistencias;

    @Column(name = "tarjetas_amarillas")
    private Integer tarjetasAmarillas;

    @Column(name = "tarjetas_rojas")
    private Integer tarjetasRojas;

    @Column(name = "minutos_jugados")
    private Integer minutosJugados;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
