package com.sportbeat.equipo_jugador_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "ligas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Liga {

    @Id
    private UUID id;

    @Column(name = "league_code", unique = true, nullable = false)
    private String leagueCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String sport;

    @Column(nullable = false)
    private String season;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Usuario admin; // entidad Usuario, no UUID

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
