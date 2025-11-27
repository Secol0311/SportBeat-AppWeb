package com.sportbeat.equipo_jugador_service.service;

import com.sportbeat.equipo_jugador_service.dto.LigaRequest;
import com.sportbeat.equipo_jugador_service.model.Liga;
import com.sportbeat.equipo_jugador_service.model.Usuario;
import com.sportbeat.equipo_jugador_service.repository.LigaRepository;
import com.sportbeat.equipo_jugador_service.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LigaService {

    @Autowired
    private LigaRepository ligaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Liga crearLiga(LigaRequest request) {
        // Verifica si existe el código de liga
        final boolean exists = ligaRepository.findByLeagueCode(request.getLeagueCode()).isPresent();
        if (exists) {
            throw new IllegalArgumentException("El código de liga ya existe.");
        }

        // Obtiene el admin como entidad
        Usuario admin = usuarioRepository.findById(request.getAdminId())
                .orElseThrow(() -> new RuntimeException("Usuario admin no encontrado"));

        // Crea la liga
        Liga liga = Liga.builder()
                .leagueCode(request.getLeagueCode())
                .name(request.getName())
                .sport(request.getSport())
                .season(request.getSeason())
                .admin(admin)  // Asume que tu Liga tiene 'Usuario admin' en vez de UUID
                .build();

        return ligaRepository.save(liga);
    }

    public List<Liga> obtenerTodas() {
        return ligaRepository.findAll();
    }

    public Liga obtenerPorId(UUID id) {
        return ligaRepository.findById(id).orElse(null);
    }

    public Liga obtenerPorLeagueCode(String leagueCode) {
        return ligaRepository.findByLeagueCode(leagueCode).orElse(null);
    }
    public long contarLigas() {
    return ligaRepository.count();
}

    public Liga actualizarLiga(UUID id, LigaRequest request) {
        Usuario admin = usuarioRepository.findById(request.getAdminId())
                .orElseThrow(() -> new RuntimeException("Usuario admin no encontrado"));

        // Evita errores de compilación usando variable final en lambda
        final Usuario finalAdmin = admin;
        return ligaRepository.findById(id).map(liga -> {
            liga.setLeagueCode(request.getLeagueCode());
            liga.setName(request.getName());
            liga.setSport(request.getSport());
            liga.setSeason(request.getSeason());
            liga.setAdmin(finalAdmin);
            return ligaRepository.save(liga);
        }).orElseThrow(() -> new RuntimeException("Liga no encontrada"));
    }

    public void eliminarLiga(UUID id) {
        ligaRepository.deleteById(id);
    }
}
