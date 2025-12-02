// En: gateway/src/main/java/com/sportbeat/gateway/serviceclient/LigaServiceClient.java
package com.sportbeat.gateway.serviceclient;

import com.sportbeat.gateway.dto.CrearLigaRequest;
import com.sportbeat.gateway.dto.LigaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class LigaServiceClient {

    private final WebClient webClient;
    private final String equipoJugadorServiceUrl;

    public LigaServiceClient(WebClient.Builder webClientBuilder, @Value("${urls.servicios.equipo-jugador}") String equipoJugadorServiceUrl) {
        this.webClient = webClientBuilder.build();
        this.equipoJugadorServiceUrl = equipoJugadorServiceUrl;
    }

    public Flux<LigaDTO> findAllLigas() {
        return this.webClient.get()
                .uri(equipoJugadorServiceUrl + "/api/ligas")
                .retrieve()
                .bodyToFlux(LigaDTO.class);
    }

    public Mono<LigaDTO> findLigaById(UUID id) {
        return this.webClient.get()
                .uri(equipoJugadorServiceUrl + "/api/ligas/{id}", id)
                .retrieve()
                .bodyToMono(LigaDTO.class);
    }

    public Mono<LigaDTO> crearLiga(CrearLigaRequest request) {
        return this.webClient.post()
                .uri(equipoJugadorServiceUrl + "/api/ligas")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(LigaDTO.class);
    }

    public Mono<LigaDTO> actualizarLiga(UUID id, CrearLigaRequest request) {
        return this.webClient.put()
                .uri(equipoJugadorServiceUrl + "/api/ligas/{id}", id)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(LigaDTO.class);
    }

    public Mono<Void> eliminarLiga(UUID id) {
        return this.webClient.delete()
                .uri(equipoJugadorServiceUrl + "/api/ligas/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}