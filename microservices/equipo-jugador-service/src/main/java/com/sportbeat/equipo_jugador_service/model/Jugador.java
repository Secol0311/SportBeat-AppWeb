package com.sportbeat.equipo_jugador_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "jugadores")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Jugador {
    @Id
    private UUID id;

   @ManyToOne
@JoinColumn(name = "usuario_id", nullable = false)
private Usuario usuario;

    @ManyToOne
@JoinColumn(name = "team_id")
private Equipo team;

    @Column(name = "jersey_number")
    private Integer jerseyNumber;

    private String position;

    @Column(name = "height_cm")
    private Integer heightCm;

    @Column(name = "weight_kg")
    private Integer weightKg;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
