// En: gateway/src/main/java/com/sportbeat/gateway/controller/DashboardController.java
package com.sportbeat.gateway.controller;

import com.sportbeat.gateway.service.AuthenticationHelper;
import com.sportbeat.gateway.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired private DashboardService dashboardService;
    @Autowired private AuthenticationHelper authHelper;

    @GetMapping("/administrador")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<String> dashboardAdministrador(Model model) {
        return dashboardService.getAdminDashboardData()
                .flatMap(data -> {
                    model.addAllAttributes(data);
                    model.addAttribute("titulo", "Panel Administrativo");
                    return Mono.just("dashboard/administrador");
                });
    }

    @GetMapping("/entrenador")
    @PreAuthorize("hasRole('ENTRENADOR')")
    public Mono<String> dashboardEntrenador(Model model) {
        UUID entrenadorId = authHelper.getCurrentUserId();
        return dashboardService.getEntrenadorDashboardData(entrenadorId)
                .flatMap(data -> {
                    model.addAllAttributes(data);
                    model.addAttribute("titulo", "Panel de Entrenador");
                    return Mono.just("dashboard/entrenador");
                });
    }

    @GetMapping("/jugador")
    @PreAuthorize("hasRole('JUGADOR')")
    public Mono<String> dashboardJugador(Model model) {
        UUID jugadorId = authHelper.getCurrentUserId();
        return dashboardService.getJugadorDashboardData(jugadorId)
                .flatMap(data -> {
                    model.addAllAttributes(data);
                    model.addAttribute("titulo", "Mi Panel de Jugador");
                    return Mono.just("dashboard/jugador");
                });
    }
}