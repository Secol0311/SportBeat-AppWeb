package com.sportbeat.gateway.dto;

import com.sportbeat.gateway.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UsuarioDTO {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private LocalDateTime createdAt;
}
