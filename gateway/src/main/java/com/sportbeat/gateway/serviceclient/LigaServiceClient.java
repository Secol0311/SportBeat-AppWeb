package com.sportbeat.gateway.serviceclient;

import com.sportbeat.gateway.dto.LigaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LigaServiceClient {

    private final WebClient webClient;
    private final String ligaServiceUrl;

    @Autowired
    public LigaServiceClient(WebClient.Builder webClientBuilder, @Value("${liga.service.url:http://localhost:8082}") String ligaServiceUrl) {
        this.webClient = webClientBuilder.build();
        this.ligaServiceUrl = ligaServiceUrl;
    }

    public Flux<LigaDTO> findAllLigas() {
        return this.webClient.get()
                .uri(ligaServiceUrl + "/api/ligas")
                .retrieve()
                .bodyToFlux(LigaDTO.class);
    }

    public Mono<LigaDTO> findLigaById(Long id) {
        return this.webClient.get()
                .uri(ligaServiceUrl + "/api/ligas/{id}", id)
                .retrieve()
                .bodyToMono(LigaDTO.class);
    }
}