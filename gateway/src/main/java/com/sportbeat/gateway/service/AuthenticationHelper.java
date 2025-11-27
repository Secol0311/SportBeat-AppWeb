package com.sportbeat.gateway.service;

import com.sportbeat.gateway.dto.UsuarioDTO;
import com.sportbeat.gateway.serviceclient.UsuarioEquipoServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationHelper {

    @Autowired
    private UsuarioEquipoServiceClient usuarioEquipoClient;

    public UUID getCurrentUserId() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("No hay un usuario autenticado.");
        }

        String username = auth.getName();

        UsuarioDTO usuario = usuarioEquipoClient.findByUsername(username).block();

        if (usuario == null) {
            throw new IllegalStateException("El usuario no existe en el sistema.");
        }

        return usuario.getId();
    }

    public String getCurrentUserRole() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("No hay un usuario autenticado.");
        }

        return auth.getAuthorities().stream()
                .findFirst()
                .map(r -> r.getAuthority().replace("ROLE_", ""))
                .orElse("UNKNOWN");
    }
}
