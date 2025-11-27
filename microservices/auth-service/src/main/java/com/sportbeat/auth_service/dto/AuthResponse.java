package com.sportbeat.auth_service.dto;

import com.sportbeat.auth_service.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String username;
    private Rol rol;
}