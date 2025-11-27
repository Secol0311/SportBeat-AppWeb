package com.sportbeat.auth_service.dto;

import com.sportbeat.auth_service.model.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistroRequest {
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    @Email(message = "El email debe ser válido")
    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    @NotNull(message = "El rol es obligatorio")
    private Rol rol;
}