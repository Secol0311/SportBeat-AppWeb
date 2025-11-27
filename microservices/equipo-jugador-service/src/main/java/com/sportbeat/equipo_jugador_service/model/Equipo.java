package com.sportbeat.equipo_jugador_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipos")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Equipo {
    @Id 
    private UUID id;

    @Column(nullable = false) 
    private String name;

    private String district;
    private String department;

    @Column(name = "league_id") 
    private UUID leagueId;

    // Relación con Usuario como coach
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id")  // Esta columna reemplaza a coachId
    private Usuario coach;

    @Column(name = "founded_year") 
    private Integer foundedYear;

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
