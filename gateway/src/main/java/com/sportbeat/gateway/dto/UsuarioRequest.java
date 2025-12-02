package com.sportbeat.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String role;  // “PLAYER”, “COACH”, “ADMIN”
}
