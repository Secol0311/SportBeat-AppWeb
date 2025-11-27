package com.sportbeat.equipo_jugador_service.dto;

import java.util.UUID;
import com.sportbeat.equipo_jugador_service.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioRequest {
    private UUID id; // Para actualizaciones
    @NotBlank private String username;
    private String password; // Solo para creación
    @Email @NotBlank private String email;
    @NotBlank private String telefono;
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    private Role role;
}