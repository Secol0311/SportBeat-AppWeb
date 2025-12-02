// En: gateway/src/main/java/com/sportbeat/gateway/controller/PartidoController.java
package com.sportbeat.gateway.controller;

import com.sportbeat.gateway.dto.CrearPartidoRequest;
import com.sportbeat.gateway.dto.CrearResultadoRequest;
import com.sportbeat.gateway.dto.PartidoDTO;
import com.sportbeat.gateway.service.PartidoService;
import com.sportbeat.gateway.service.ResultadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Controller
public class PartidoController {

    @Autowired private PartidoService partidoService;
    @Autowired private ResultadoService resultadoService;

    // --- VISTAS PÚBLICAS ---
    @GetMapping("/partidos/calendario")
    public Mono<String> calendarioPartidosPage(Model model) {
        model.addAttribute("titulo", "Calendario de Partidos");
        return Mono.just("partidos/calendario");
    }

    @GetMapping("/ligas/{idLiga}/calendario")
    public Mono<String> calendarioPorLigaPage(@PathVariable UUID idLiga, Model model) {
        return partidoService.getCalendarioByLiga(idLiga)
                .collectList()
                .flatMap(partidos -> {
                    model.addAttribute("partidos", partidos);
                    model.addAttribute("titulo", "Calendario de Partidos de la Liga");
                    model.addAttribute("idLiga", idLiga);
                    return Mono.just("partidos/calendario");
                });
    }

    @GetMapping("/partidos/detalle/{id}")
    public Mono<String> detallePartidoPage(@PathVariable UUID id, Model model) {
        return partidoService.getDetallePartido(id)
                .flatMap(partido -> {
                    model.addAttribute("partido", partido);
                    model.addAttribute("titulo", "Detalle del Partido");
                    return Mono.just("partidos/detalle");
                })
                .switchIfEmpty(Mono.just("redirect:/partidos/calendario?error=notFound"));
    }

    // --- VISTAS Y ACCIONES (Protegidas) ---

    @GetMapping("/partidos/{id}/registrar-resultado")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'ENTRENADOR')")
    public Mono<String> mostrarFormularioResultado(@PathVariable UUID id, Model model) {
        return partidoService.getDetallePartido(id)
                .flatMap(partido -> {
                    model.addAttribute("partido", partido);
                    model.addAttribute("resultado", new CrearResultadoRequest());
                    model.addAttribute("titulo", "Registrar Resultado del Partido");
                    return Mono.just("partidos/registrar-resultado");
                })
                .switchIfEmpty(Mono.just("redirect:/partidos/calendario?error=notFound"));
    }

    @PostMapping("/partidos/{id}/resultado")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'ENTRENADOR')")
    public Mono<String> registrarResultado(@PathVariable UUID id, @ModelAttribute CrearResultadoRequest request) {
        request.setPartidoId(id); // Asociamos el resultado al partido correcto
        return resultadoService.crearResultado(request)
                .then(Mono.just("redirect:/partidos/detalle/" + id))
                .onErrorReturn("redirect:/partidos/calendario?error=true");
    }

    @PostMapping("/partidos")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'ENTRENADOR')")
    public Mono<String> crearPartido(@ModelAttribute CrearPartidoRequest request) {
        return partidoService.crearPartido(request)
                .then(Mono.just("redirect:/dashboard/entrenador")) // O el dashboard del admin
                .onErrorReturn("redirect:/dashboard/entrenador?error=partido");
    }

    @GetMapping("/partidos/{id}/eliminar")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'ENTRENADOR')")
    public Mono<String> eliminarPartido(@PathVariable UUID id) {
        return partidoService.eliminarPartido(id)
                .then(Mono.just("redirect:/dashboard/entrenador"))
                .onErrorReturn("redirect:/dashboard/entrenador?error=partido");
    }
}