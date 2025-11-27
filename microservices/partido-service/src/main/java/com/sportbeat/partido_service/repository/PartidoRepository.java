package com.sportbeat.partido_service.repository;

import com.sportbeat.partido_service.model.EstadoPartido;
import com.sportbeat.partido_service.model.Partido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PartidoRepository extends JpaRepository<Partido, UUID> {

    // Obtiene el calendario completo de una liga, ordenado por fecha
    List<Partido> findByLigaIdOrderByFechaAscHoraAsc(UUID ligaId);

    // Obtiene todos los partidos de un equipo (como local o visitante)
    List<Partido> findByEquipoLocalIdOrEquipoVisitanteIdOrderByFechaAsc(UUID equipoLocalId, UUID equipoVisitanteId);

    // Obtiene los partidos de una liga en un estado específico
    List<Partido> findByLigaIdAndEstado(UUID ligaId, EstadoPartido estado);
}