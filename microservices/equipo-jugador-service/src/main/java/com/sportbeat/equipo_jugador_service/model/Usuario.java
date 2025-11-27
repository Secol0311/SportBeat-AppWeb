package com.sportbeat.equipo_jugador_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String username;

    private String password_hash;

    private String email;

    private String telefono;

    private String first_name;

    private String last_name;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String created_at;
    private String updated_at;
}
