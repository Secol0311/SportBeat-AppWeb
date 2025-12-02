package com.sportbeat.gateway.serviceclient;

import com.sportbeat.gateway.dto.CrearPartidoRequest;
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

   public Mono<PartidoDTO> crearPartido(CrearPartidoRequest request) {
    return webClient.post()
            .uri(partidoServiceUrl + "/api/partidos")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(PartidoDTO.class);
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

    public Flux<PartidoDTO> findProximosPartidosDeEntrenador(UUID entrenadorId) {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/proximos/entrenador/{entrenadorId}", entrenadorId)
                .retrieve()
                .bodyToFlux(PartidoDTO.class);
    }

    public Flux<PartidoDTO> findPartidosRecientes() {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/recientes")
                .retrieve()
                .bodyToFlux(PartidoDTO.class);
    }
    public Flux<PartidoDTO> findPartidosRecientesDeJugador(UUID jugadorId) {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/recientes/jugador/{jugadorId}", jugadorId)
                .retrieve()
                .bodyToFlux(PartidoDTO.class);
    }
    public Flux<PartidoDTO> findPartidosRecientesDeEntrenador(UUID entrenadorId) {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/recientes/entrenador/{entrenadorId}", entrenadorId)
                .retrieve()
                .bodyToFlux(PartidoDTO.class);
    }
    public Mono<Long> countPartidos() {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/count")
                .retrieve()
                .bodyToMono(Long.class);
    }
    public Mono<Long> countPartidosDeJugador(UUID jugadorId) {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/count/jugador/{jugadorId}", jugadorId)
                .retrieve()
                .bodyToMono(Long.class);
    }
    public Mono<Long> countPartidosDeEntrenador(UUID entrenadorId) {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/count/entrenador/{entrenadorId}", entrenadorId)
                .retrieve()
                .bodyToMono(Long.class);
    }
    public Mono<Long> countPartidosPorEstado(String estado) {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/count/estado/{estado}", estado)
                .retrieve()
                .bodyToMono(Long.class);
    }
    public Mono<Long> countPartidosPorEstadoDeJugador(UUID jugadorId, String estado) {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/count/jugador/{jugadorId}/estado/{estado}", jugadorId, estado)
                .retrieve()
                .bodyToMono(Long.class);
    }
    public Mono<Long> countPartidosPorEstadoDeEntrenador(UUID entrenadorId, String estado) {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/count/entrenador/{entrenadorId}/estado/{estado}", entrenadorId, estado)
                .retrieve()
                .bodyToMono(Long.class);
    }
    public Flux<PartidoDTO> findPartidosByEstado(String estado) {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/estado/{estado}", estado)
                .retrieve()
                .bodyToFlux(PartidoDTO.class);
    }
    public Flux<PartidoDTO> findPartidosByEstadoDeJugador(UUID jugadorId, String estado) {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/jugador/{jugadorId}/estado/{estado}", jugadorId, estado)
                .retrieve()
                .bodyToFlux(PartidoDTO.class);
    }
    public Flux<PartidoDTO> findPartidosByEstadoDeEntrenador(UUID entrenadorId, String estado) {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/entrenador/{entrenadorId}/estado/{estado}", entrenadorId, estado)
                .retrieve()
                .bodyToFlux(PartidoDTO.class);
    }
    public Flux<PartidoDTO> findTodosPartidos() {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/todos")
                .retrieve()
                .bodyToFlux(PartidoDTO.class);
    }
    public Flux<PartidoDTO> findTodosPartidosDeJugador(UUID jugadorId) {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/todos/jugador/{jugadorId}", jugadorId)
                .retrieve()
                .bodyToFlux(PartidoDTO.class);
    }
    public Flux<PartidoDTO> findTodosPartidosDeEntrenador(UUID entrenadorId) {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/todos/entrenador/{entrenadorId}", entrenadorId)
                .retrieve()
                .bodyToFlux(PartidoDTO.class);
    }
    public Mono<PartidoDTO> actualizarPartido(UUID id, PartidoDTO partidoDTO) {
        return this.webClient.put()
                .uri(partidoServiceUrl + "/api/partidos/{id}", id)
                .bodyValue(partidoDTO)
                .retrieve()
                .bodyToMono(PartidoDTO.class);
    }
    public Mono<Void> eliminarPartido(UUID partidoId) {
    return webClient.delete()
            .uri(partidoServiceUrl + "/api/partidos/{id}", partidoId)
            .retrieve()
            .bodyToMono(Void.class);
}
}