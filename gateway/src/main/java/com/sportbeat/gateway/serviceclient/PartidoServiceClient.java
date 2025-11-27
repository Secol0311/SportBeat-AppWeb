package com.sportbeat.gateway.serviceclient;

import com.sportbeat.gateway.dto.PartidoDTO;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PartidoServiceClient {

    private final WebClient webClient;
    private final String partidoServiceUrl;

    public PartidoServiceClient(WebClient.Builder webClientBuilder, @Value("${urls.servicios.partido}") String partidoServiceUrl) {
        this.webClient = webClientBuilder.build();
        this.partidoServiceUrl = partidoServiceUrl;
    }

    public Flux<PartidoDTO> findCalendarioByLiga(UUID ligaId) {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/calendario/liga/{ligaId}", ligaId)
                .retrieve()
                .bodyToFlux(PartidoDTO.class);
    }

    public Mono<PartidoDTO> findPartidoById(UUID id) {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/{id}", id)
                .retrieve()
                .bodyToMono(PartidoDTO.class);
    }

    // --- Métodos adicionales para Dashboards ---
    // NOTA: Estos endpoints deben existir en el partido-service

    public Flux<PartidoDTO> findProximosPartidos() {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/proximos")
                .retrieve()
                .bodyToFlux(PartidoDTO.class);
    }

    public Flux<PartidoDTO> findProximosPartidosDeJugador(UUID jugadorId) {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/proximos/jugador/{jugadorId}", jugadorId)
                .retrieve()
                .bodyToFlux(PartidoDTO.class);
    }
}