// En: gateway/src/main/java/com/sportbeat/gateway/controller/LigaController.java
package com.sportbeat.gateway.controller;

import com.sportbeat.gateway.dto.CrearLigaRequest;
import com.sportbeat.gateway.dto.LigaDTO;
import com.sportbeat.gateway.service.LigaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Controller
public class LigaController {

    @Autowired
    private LigaService ligaService;

    // --- VISTAS PÚBLICAS ---

    /**
     * Muestra el listado público de todas las ligas.
     * @param model El modelo de Thymeleaf.
     * @return Un Mono que emite el nombre de la vista.
     */
    @GetMapping("/ligas")
    public Mono<String> listadoPublicoLigas(Model model) {
        return ligaService.findAllLigas()
                .flatMap(listaLigas -> {
                    model.addAttribute("ligas", listaLigas);
                    model.addAttribute("titulo", "Ligas Disponibles");
                    return Mono.just("ligas/listado"); // Vista simple, sin botones de acción
                })
                .onErrorResume(e -> Mono.just("error/500")); // Manejo de errores simple
    }

    // --- VISTAS Y ACCIONES DE ADMINISTRADOR (Protegidas por Seguridad) ---

    /**
     * Muestra el dashboard de administración de ligas.
     * NOTA: La protección de esta ruta se maneja en SecurityConfig con .hasRole("ADMINISTRADOR")
     */
    @GetMapping("/admin/ligas")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<String> dashboardAdminLigas(Model model) {
        // Cargamos los datos necesarios para el dashboard del admin
        Mono<CrearLigaRequest> formMono = Mono.just(new CrearLigaRequest());

        return ligaService.findAllLigas()
                .zipWith(formMono)
                .flatMap(tuple -> {
                    model.addAttribute("ligas", tuple.getT1()); // Lista de ligas
                    model.addAttribute("ligaForm", tuple.getT2()); // Formulario vacío para crear
                    model.addAttribute("titulo", "Administrar Ligas");
                    return Mono.just("dashboard/admin-ligas"); // Vista que combina lista y formulario
                });
    }

    /**
     * Procesa el formulario de creación de una nueva liga.
     */
    @PostMapping("/admin/ligas")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<String> crearLiga(@ModelAttribute CrearLigaRequest request) {
        return ligaService.crearLiga(request)
                .then(Mono.just("redirect:/admin/ligas")) // Si tiene éxito, redirige al dashboard
                .onErrorReturn("redirect:/admin/ligas?error=true"); // Si hay error, redirige con un flag
    }

    /**
     * Muestra el formulario para editar una liga existente.
     */
    @GetMapping("/admin/ligas/{id}/editar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<String> mostrarEditarLiga(@PathVariable UUID id, Model model) {
        return ligaService.getLigaById(id)
                .flatMap(liga -> {
                    model.addAttribute("ligaForm", liga); // El formulario se rellena con los datos de la liga
                    model.addAttribute("titulo", "Editar Liga");
                    return Mono.just("dashboard/admin-form-liga"); // Vista de formulario
                })
                .switchIfEmpty(Mono.just("redirect:/admin/ligas?error=notFound")); // Si no la encuentra
    }

    /**
     * Procesa el formulario de edición de una liga.
     */
    @PostMapping("/admin/ligas/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<String> actualizarLiga(@PathVariable UUID id, @ModelAttribute CrearLigaRequest request) {
        return ligaService.actualizarLiga(id, request)
                .then(Mono.just("redirect:/admin/ligas"))
                .onErrorReturn("redirect:/admin/ligas?error=true");
    }

    /**
     * Elimina una liga por su ID.
     */
    @GetMapping("/admin/ligas/{id}/eliminar")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public Mono<String> eliminarLiga(@PathVariable UUID id) {
        return ligaService.eliminarLiga(id)
                .then(Mono.just("redirect:/admin/ligas"))
                .onErrorReturn("redirect:/admin/ligas?error=true");
    }
}