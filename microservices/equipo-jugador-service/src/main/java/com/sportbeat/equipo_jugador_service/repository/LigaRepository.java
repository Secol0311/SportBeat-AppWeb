package com.sportbeat.equipo_jugador_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sportbeat.equipo_jugador_service.model.Liga;

public interface LigaRepository extends JpaRepository<Liga, UUID> {
    Optional<Liga> findByLeagueCode(String leagueCode);
}
