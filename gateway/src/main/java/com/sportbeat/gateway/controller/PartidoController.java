package com.sportbeat.gateway.controller;

import com.sportbeat.gateway.dto.PartidoDTO;
import com.sportbeat.gateway.service.PartidoService;
import com.sportbeat.gateway.service.ResultadoService;
import com.sportbeat.gateway.dto.CrearResultadoRequest;
import com.sportbeat.gateway.dto.ResultadoDTO;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.UUID;

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
    @Autowired
    private ResultadoService resultadoService;

    @GetMapping("/partidos/{id}/registrar-resultado")
    public String mostrarFormularioResultado(@PathVariable UUID id, Model model) {
        Mono<PartidoDTO> partidoMono = partidoService.getDetallePartido(id);
        PartidoDTO partido = partidoMono.block();
        if (partido != null) {
            model.addAttribute("partido", partido);
            model.addAttribute("resultado", new ResultadoDTO());
            model.addAttribute("titulo", "Registrar Resultado del Partido");
            return "partidos/registrar-resultado";
        }
        return "redirect:/partidos/calendario";
    }

    @PostMapping("/partidos/{id}/registrar-resultado")
    public String registrarResultado(@PathVariable UUID id, @ModelAttribute CrearResultadoRequest request) {
        request.setPartidoId(id);
        resultadoService.crearResultado(request);
        
        return "redirect:/partidos/detalle/" + id;
    }

    @GetMapping("/partidos/calendario")
    public String calendarioPartidosPage(Model model) {
        // Aquí podrías obtener el calendario de todas las ligas o de una liga específica
        // Por ahora, un ejemplo genérico
        model.addAttribute("titulo", "Calendario de Partidos");
        return "partidos/calendario";
    }

    @GetMapping("/ligas/{idLiga}/calendario")
    public String calendarioPorLigaPage(@PathVariable UUID idLiga, Model model) {
        partidoService.getCalendarioByLiga(idLiga)
                .collectList()
                .doOnNext(partidos -> model.addAttribute("partidos", partidos))
                .block();
        
        model.addAttribute("titulo", "Calendario de Partidos de la Liga");
        model.addAttribute("idLiga", idLiga);
        return "partidos/calendario";
    }

    @GetMapping("/partidos/detalle/{id}")
    public String detallePartidoPage(@PathVariable UUID id, Model model) {
        PartidoDTO partido = partidoService.getDetallePartido(id).block();
        if (partido != null) {
            model.addAttribute("partido", partido);
            model.addAttribute("titulo", "Detalle del Partido");
            return "partidos/detalle";
        }
        return "redirect:/partidos/calendario";
    }
}