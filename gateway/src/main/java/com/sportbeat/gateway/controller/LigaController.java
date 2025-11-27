package com.sportbeat.gateway.controller;

import com.sportbeat.gateway.dto.LigaDTO;
import com.sportbeat.gateway.service.LigaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@Controller
public class LigaController {

    @Autowired
    private LigaService ligaService;

    @GetMapping("/ligas")
    public String listadoLigasPage(Model model) {
        // Obtenemos los datos de forma reactiva y los añadimos al modelo cuando estén listos
        Mono<Void> mono = ligaService.getAllLigas()
                .collectList()
                .doOnNext(ligas -> model.addAttribute("ligas", ligas))
                .then();

        // En un controlador de Spring MVC con Thymeleaf, podemos suscribirnos y bloquear hasta que el Mono se complete.
        // Esto es aceptable en el contexto de un BFF que renderiza una vista.
        mono.block(); // ¡Ojo! block() es generally discouraged in reactive code, but it's a pragmatic solution here.

        model.addAttribute("titulo", "Listado de Ligas Deportivas");
        return "ligas/listado";
    }

    @GetMapping("/ligas/{id}")
    public String detalleLigaPage(@PathVariable Long id, Model model) {
        LigaDTO liga = ligaService.getLigaById(id).block(); // Similar a lo anterior
        if (liga != null) {
            model.addAttribute("liga", liga);
            model.addAttribute("titulo", "Detalle de la Liga: " + liga.getNombre());
            return "ligas/detalle";
        }
        // Manejar el caso de que la liga no exista
        return "redirect:/ligas"; 
    }
}