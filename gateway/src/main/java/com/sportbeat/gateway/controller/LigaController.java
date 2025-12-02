package com.sportbeat.gateway.controller;

import com.sportbeat.gateway.service.LigaService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import com.sportbeat.gateway.dto.CrearLigaRequest;
import com.sportbeat.gateway.dto.LigaDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LigaController {
    @Autowired private LigaService ligaService;

    // --- LEER ---
    @GetMapping("/ligas")
    public String listadoLigasPage(Model model) { 
        model.addAttribute("ligas", ligaService.findAllLigas());
        model.addAttribute("titulo", "Listado de Ligas");
        return "ligas/listado-ligas";
    }

    // --- CREAR ---
    @GetMapping("/ligas/nueva")
    public String mostrarFormularioNuevaLiga(Model model) {
        model.addAttribute("liga", new CrearLigaRequest());
        model.addAttribute("titulo", "Crear Nueva Liga");
        return "ligas/formulario-liga";
    }

    @PostMapping("/ligas")
    public String crearLiga(@ModelAttribute CrearLigaRequest request) {
        ligaService.crearLiga(request);
        return "redirect:/ligas";
    }

    // --- EDITAR ---
    @GetMapping("/ligas/{id}/editar")
    public String mostrarFormularioEditarLiga(@PathVariable UUID id, Model model) {
        LigaDTO liga = ligaService.getLigaById(id);
        model.addAttribute("liga", liga);
        model.addAttribute("titulo", "Editar Liga");
        return "ligas/formulario-liga";
    }

    // --- ELIMINAR ---
    @GetMapping("/ligas/{id}/eliminar")
    public String eliminarLiga(@PathVariable UUID id) {
        ligaService.eliminarLiga(id);
        return "redirect:/ligas";
    }
}