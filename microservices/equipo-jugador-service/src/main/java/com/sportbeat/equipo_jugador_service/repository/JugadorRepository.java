package com.sportbeat.equipo_jugador_service.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sportbeat.equipo_jugador_service.model.Jugador;

public interface JugadorRepository extends JpaRepository<Jugador, UUID> {
    List<Jugador> findByTeam_Id(UUID teamId);
Optional<Jugador> findByUsuario_Id(UUID usuarioId);
}