package com.sportbeat.resultado_service.repository;

import com.sportbeat.resultado_service.model.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface ResultadoRepository extends JpaRepository<Resultado, UUID> {
    Optional<Resultado> findByPartidoId(UUID partidoId);
    boolean existsByPartidoId(UUID partidoId);
}