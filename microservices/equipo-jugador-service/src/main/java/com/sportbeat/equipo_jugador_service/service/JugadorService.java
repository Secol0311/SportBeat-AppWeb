package com.sportbeat.equipo_jugador_service.service;

import com.sportbeat.equipo_jugador_service.dto.JugadorRequest;
import com.sportbeat.equipo_jugador_service.model.Jugador;
import com.sportbeat.equipo_jugador_service.model.Usuario;
import com.sportbeat.equipo_jugador_service.model.Equipo;
import com.sportbeat.equipo_jugador_service.repository.JugadorRepository;
import com.sportbeat.equipo_jugador_service.repository.UsuarioRepository;
import com.sportbeat.equipo_jugador_service.repository.EquipoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    public Jugador crearJugador(JugadorRequest request) {

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Equipo team = (request.getTeamId() != null)
                ? equipoRepository.findById(request.getTeamId())
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"))
                : null;

        Jugador jugador = Jugador.builder()
                .usuario(usuario)
                .team(team)
                .jerseyNumber(request.getJerseyNumber())
                .position(request.getPosition())
                .heightCm(request.getHeightCm())
                .weightKg(request.getWeightKg())
                .build();

        return jugadorRepository.save(jugador);
    }

    public List<Jugador> obtenerTodos() {
        return jugadorRepository.findAll();
    }

    public Jugador obtenerPorId(UUID id) {
        return jugadorRepository.findById(id).orElse(null);
    }

    public Jugador obtenerPorUsuarioId(UUID usuarioId) {
        return jugadorRepository.findByUsuario_Id(usuarioId).orElse(null);
    }

    public List<Jugador> obtenerPorTeamId(UUID teamId) {
        return jugadorRepository.findByTeam_Id(teamId);
    }

    public Jugador actualizarJugador(UUID id, JugadorRequest request) {

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Equipo team = (request.getTeamId() != null)
                ? equipoRepository.findById(request.getTeamId())
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"))
                : null;

        return jugadorRepository.findById(id).map(jugador -> {
            jugador.setUsuario(usuario);
            jugador.setTeam(team);
            jugador.setJerseyNumber(request.getJerseyNumber());
            jugador.setPosition(request.getPosition());
            jugador.setHeightCm(request.getHeightCm());
            jugador.setWeightKg(request.getWeightKg());
            return jugadorRepository.save(jugador);
        }).orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
    }

    public void eliminarJugador(UUID id) {
        jugadorRepository.deleteById(id);
    }
}
