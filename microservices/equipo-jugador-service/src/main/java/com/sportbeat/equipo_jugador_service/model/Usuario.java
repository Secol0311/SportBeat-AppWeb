package com.sportbeat.equipo_jugador_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.time.LocalDateTime;


@Entity
@Table(name = "usuarios")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Usuario {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(unique = true, nullable = false) private String username;
    @Column(name = "password_hash", nullable = false) private String passwordHash;
    @Column(unique = true, nullable = false) private String email;
    @Column(unique = true, nullable = false) private String telefono;
    @Column(name = "first_name", nullable = false) private String firstName;
    @Column(name = "last_name", nullable = false) private String lastName;
    @Enumerated(EnumType.STRING) private Role role;
    @Column(name = "created_at", updatable = false) private LocalDateTime createdAt;
    @Column(name = "updated_at") private LocalDateTime updatedAt;
    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = LocalDateTime.now(); }
}