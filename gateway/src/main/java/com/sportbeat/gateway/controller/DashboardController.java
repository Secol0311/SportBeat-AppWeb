package com.sportbeat.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping("/jugador")
    public String dashboardJugador() {
        return "dashboard/jugador"; // Apunta a: templates/dashboard/jugador.html
    }

    @GetMapping("/entrenador")
    public String dashboardEntrenador() {
        return "dashboard/entrenador"; // Apunta a: templates/dashboard/entrenador.html
    }

    @GetMapping("/administrador")
    public String dashboardAdministrador() {
        return "dashboard/administrador"; // Apunta a: templates/dashboard/administrador.html
    }
}