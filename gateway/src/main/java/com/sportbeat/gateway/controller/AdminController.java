// En: gateway/src/main/java/com/sportbeat/gateway/controller/AdminController.java
package com.sportbeat.gateway.controller;

import com.sportbeat.gateway.dto.*;
import com.sportbeat.gateway.service.DashboardService;
import com.sportbeat.gateway.serviceclient.UsuarioEquipoServiceClient;
import com.sportbeat.gateway.serviceclient.PartidoServiceClient;
import com.sportbeat.gateway.serviceclient.ResultadoServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private DashboardService dashboardService;
    @Autowired private UsuarioEquipoServiceClient usuarioEquipoClient;
    @Autowired private PartidoServiceClient partidoClient;
    @Autowired private ResultadoServiceClient resultadoClient;

    // --- USUARIOS ---
    @PostMapping("/registrar-usuario")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<String> registrarUsuario(@ModelAttribute CrearUsuarioRequest request) {
        // CAMBIO: Usamos .then() para ejecutar una acción después de que el Mono se complete
        return usuarioEquipoClient.crearUsuario(request)
                .then(Mono.just("redirect:/dashboard/administrador"))
                .onErrorReturn("redirect:/dashboard/administrador?error=usuario"); // Manejo de error simple
    }

   

    // --- PARTIDOS ---
    @PostMapping("/partidos")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<String> crearPartido(@ModelAttribute CrearPartidoRequest request) {
        return partidoClient.crearPartido(request)
                .then(Mono.just("redirect:/dashboard/administrador"))
                .onErrorReturn("redirect:/dashboard/administrador?error=partido");
    }

    @GetMapping("/partidos/{id}/eliminar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<String> eliminarPartido(@PathVariable UUID id) {
        return partidoClient.eliminarPartido(id)
                .then(Mono.just("redirect:/dashboard/administrador"))
                .onErrorReturn("redirect:/dashboard/administrador?error=partido");
    }

    // --- RESULTADOS ---
    @PostMapping("/registrar-resultado")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<String> seleccionarPartidoParaResultado(@ModelAttribute CrearResultadoRequest request, Model model) {
        // CAMBIO: Usamos .zip() para obtener el partido y los datos del dashboard en paralelo
        return dashboardService.getPartidoById(request.getPartidoId())
                .zipWith(dashboardService.getAdminDashboardData())
                .flatMap(tuple -> {
                    Map<String, Object> data = tuple.getT2();
                    data.put("partidoSeleccionado", tuple.getT1());
                    model.addAllAttributes(data);
                    model.addAttribute("titulo", "Panel Administrativo");
                    return Mono.just("dashboard/administrador");
                })
                .onErrorReturn("redirect:/dashboard/administrador?error=partido");
    }
}