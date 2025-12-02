// Crea gateway/src/main/java/com/sportbeat/gateway/controller/AdminController.java
package com.sportbeat.gateway.controller;


import org.springframework.stereotype.Controller;
import com.sportbeat.gateway.dto.CrearLigaRequest;
import com.sportbeat.gateway.dto.LigaDTO;   
import com.sportbeat.gateway.dto.CrearPartidoRequest;
import com.sportbeat.gateway.dto.CrearResultadoRequest;
import com.sportbeat.gateway.dto.PartidoDTO;
import com.sportbeat.gateway.service.DashboardService;
import com.sportbeat.gateway.serviceclient.UsuarioEquipoServiceClient;
import com.sportbeat.gateway.serviceclient.PartidoServiceClient;
import com.sportbeat.gateway.serviceclient.ResultadoServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.sportbeat.gateway.dto.CrearUsuarioRequest;


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
    public String registrarUsuario(@ModelAttribute CrearUsuarioRequest request) {
        usuarioEquipoClient.crearUsuario(request);
        return "redirect:/dashboard/administrador";
    }

    // --- LIGAS ---
    @PostMapping("/ligas")
    public String crearLiga(@ModelAttribute CrearLigaRequest request) {
        usuarioEquipoClient.crearLiga(request);
        return "redirect:/dashboard/administrador";
    }
    @GetMapping("/ligas/{id}/editar")
    public String mostrarEditarLiga(@PathVariable UUID id, Model model) {
        LigaDTO liga = usuarioEquipoClient.findLigaById(id).block();
        model.addAttribute("ligaForm", liga);
        // Necesitas recargar los demás datos para la vista
        model.addAllAttributes(dashboardService.getAdminDashboardData().block());
        model.addAttribute("titulo", "Panel Administrativo");
        return "dashboard/administrador";
    }
    @GetMapping("/ligas/{id}/eliminar")
    public String eliminarLiga(@PathVariable UUID id) {
        usuarioEquipoClient.eliminarLiga(id);
        return "redirect:/dashboard/administrador";
    }

    // --- PARTIDOS ---
    @PostMapping("/partidos")
    public String crearPartido(@ModelAttribute CrearPartidoRequest request) {
        partidoClient.crearPartido(request);
        return "redirect:/dashboard/administrador";
    }
    @GetMapping("/partidos/{id}/eliminar")
    public String eliminarPartido(@PathVariable UUID id) {
        partidoClient.eliminarPartido(id);
        return "redirect:/dashboard/administrador";
    }

    // --- RESULTADOS ---
    @PostMapping("/registrar-resultado")
    public String seleccionarPartidoParaResultado(@ModelAttribute CrearResultadoRequest request, Model model) {
        // Este método solo se usa para recargar la página con el partido seleccionado
        PartidoDTO partidoSeleccionado = dashboardService.getPartidoById(request.getPartidoId()).block();
        Map<String, Object> data = dashboardService.getAdminDashboardData().block();
        data.put("partidoSeleccionado", partidoSeleccionado);
        model.addAllAttributes(data);
        model.addAttribute("titulo", "Panel Administrativo");
        return "dashboard/administrador";
    }
}