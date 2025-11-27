package com.sportbeat.gateway.serviceclient;

import com.sportbeat.gateway.dto.PartidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PartidoServiceClient {

    private final WebClient webClient;
    private final String partidoServiceUrl;

    @Autowired
    public PartidoServiceClient(WebClient.Builder webClientBuilder, @Value("${partido.service.url:http://localhost:8084}") String partidoServiceUrl) {
        this.webClient = webClientBuilder.build();
        this.partidoServiceUrl = partidoServiceUrl;
    }

    public Flux<PartidoDTO> findCalendarioByLiga(Long idLiga) {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/calendario/liga/{idLiga}", idLiga)
                .retrieve()
                .bodyToFlux(PartidoDTO.class);
    }

    public Mono<PartidoDTO> findPartidoById(Long id) {
        return this.webClient.get()
                .uri(partidoServiceUrl + "/api/partidos/{id}", id)
                .retrieve()
                .bodyToMono(PartidoDTO.class);
    }
}