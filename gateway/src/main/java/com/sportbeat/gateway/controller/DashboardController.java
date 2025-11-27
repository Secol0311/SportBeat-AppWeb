package com.sportbeat.gateway.controller;

import com.sportbeat.gateway.service.AuthenticationHelper;
import com.sportbeat.gateway.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private AuthenticationHelper authHelper;

    @GetMapping("/administrador")
    public String dashboardAdministrador(Model model) {
        // Verificación de seguridad adicional (defensa en profundidad)
        if (!"ADMINISTRADOR".equals(authHelper.getCurrentUserRole())) {
            return "redirect:/error/403"; // O lanzar una excepción de acceso denegado
        }

        Map<String, Object> data = dashboardService.getAdminDashboardData().block();
        model.addAllAttributes(data);
        model.addAttribute("titulo", "Panel Administrativo");
        return "dashboard/administrador";
    }

    @GetMapping("/entrenador")
    public String dashboardEntrenador(Model model) {
        if (!"ENTRENADOR".equals(authHelper.getCurrentUserRole())) {
            return "redirect:/error/403";
        }

        UUID entrenadorId = authHelper.getCurrentUserId();
        Map<String, Object> data = dashboardService.getEntrenadorDashboardData(entrenadorId).block();
        model.addAllAttributes(data);
        model.addAttribute("titulo", "Panel de Entrenador");
        return "dashboard/entrenador";
    }

    @GetMapping("/jugador")
    public String dashboardJugador(Model model) {
        if (!"JUGADOR".equals(authHelper.getCurrentUserRole())) {
            return "redirect:/error/403";
        }

        UUID jugadorId = authHelper.getCurrentUserId();
        Map<String, Object> data = dashboardService.getJugadorDashboardData(jugadorId).block();
        model.addAllAttributes(data);
        model.addAttribute("titulo", "Mi Panel de Jugador");
        return "dashboard/jugador";
    }
}