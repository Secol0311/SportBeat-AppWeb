package com.sportbeat.gateway.serviceclient;

import com.sportbeat.gateway.dto.EstadisticaJugadorDTO;
import com.sportbeat.gateway.dto.ResultadoDTO;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ResultadoServiceClient {

    private final WebClient webClient;
    private final String resultadoServiceUrl;

    public ResultadoServiceClient(WebClient.Builder webClientBuilder, @Value("${urls.servicios.resultado}") String resultadoServiceUrl) {
        this.webClient = webClientBuilder.build();
        this.resultadoServiceUrl = resultadoServiceUrl;
    }

    public Mono<ResultadoDTO> crearResultado(com.sportbeat.gateway.dto.CrearResultadoRequest request) {
        return this.webClient.post()
                .uri(resultadoServiceUrl + "/api/resultados")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ResultadoDTO.class);
    }

    public Mono<ResultadoDTO> obtenerResultadoPorPartidoId(UUID partidoId) {
        return this.webClient.get()
                .uri(resultadoServiceUrl + "/api/resultados/partido/{partidoId}", partidoId)
                .retrieve()
                .bodyToMono(ResultadoDTO.class);
    }

    public Flux<EstadisticaJugadorDTO> findEstadisticasByJugador(UUID jugadorId) {
        return this.webClient.get()
                .uri(resultadoServiceUrl + "/api/estadisticas/jugador/{jugadorId}", jugadorId)
                .retrieve()
                .bodyToFlux(EstadisticaJugadorDTO.class);
    }

    public Flux<EstadisticaJugadorDTO> findEstadisticasByPartido(UUID partidoId) {
        return this.webClient.get()
                .uri(resultadoServiceUrl + "/api/estadisticas/partido/{partidoId}", partidoId)
                .retrieve()
                .bodyToFlux(EstadisticaJugadorDTO.class);
    }
}