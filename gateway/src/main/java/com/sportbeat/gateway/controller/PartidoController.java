package com.sportbeat.gateway.controller;

import com.sportbeat.gateway.dto.PartidoDTO;
import com.sportbeat.gateway.service.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@Controller
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    @GetMapping("/partidos/calendario")
    public String calendarioPartidosPage(Model model) {
        // Aquí podrías obtener el calendario de todas las ligas o de una liga específica
        // Por ahora, un ejemplo genérico
        model.addAttribute("titulo", "Calendario de Partidos");
        return "partidos/calendario";
    }

    @GetMapping("/ligas/{idLiga}/calendario")
    public String calendarioPorLigaPage(@PathVariable Long idLiga, Model model) {
        partidoService.getCalendarioByLiga(idLiga)
                .collectList()
                .doOnNext(partidos -> model.addAttribute("partidos", partidos))
                .block();
        
        model.addAttribute("titulo", "Calendario de Partidos de la Liga");
        model.addAttribute("idLiga", idLiga);
        return "partidos/calendario";
    }

    @GetMapping("/partidos/detalle/{id}")
    public String detallePartidoPage(@PathVariable Long id, Model model) {
        PartidoDTO partido = partidoService.getDetallePartido(id).block();
        if (partido != null) {
            model.addAttribute("partido", partido);
            model.addAttribute("titulo", "Detalle del Partido");
            return "partidos/detalle";
        }
        return "redirect:/partidos/calendario";
    }
}