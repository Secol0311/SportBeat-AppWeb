package com.sportbeat.equipo_jugador_service.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sportbeat.equipo_jugador_service.model.Equipo;

public interface EquipoRepository extends JpaRepository<Equipo, UUID> {
    List<Equipo> findByLeagueId(UUID leagueId);

    // Cambiado para buscar por la entidad coach
    List<Equipo> findByCoach_Id(UUID coachId);
}
