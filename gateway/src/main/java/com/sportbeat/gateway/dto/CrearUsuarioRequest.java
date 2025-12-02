package com.sportbeat.gateway.dto;

import com.sportbeat.gateway.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la creación de un nuevo Usuario (Jugador o Entrenador).
 * Se usa en los formularios de Thymeleaf del dashboard de administrador.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrearUsuarioRequest {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Role role; // El rol se establece de forma oculta en el formulario
}