package com.sportbeat.partido_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "partidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partido {
    @Id
    private UUID id;

    @Column(name = "liga_id", nullable = false)
    private UUID ligaId;

    @Column(name = "equipo_local_id", nullable = false)
    private UUID equipoLocalId;

    @Column(name = "equipo_visitante_id", nullable = false)
    private UUID equipoVisitanteId;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "hora", nullable = false)
    private LocalTime hora;

    private String venue; // 'venue' no necesita @Column si el nombre coincide

    @Enumerated(EnumType.STRING)
    private EstadoPartido estado = EstadoPartido.PROGRAMADO; // Estado por defecto

    // Auditoría
    @Column(name = "created_at", updatable = false)
    private java.time.LocalDateTime createdAt;

    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
        updatedAt = java.time.LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = java.time.LocalDateTime.now();
    }
}