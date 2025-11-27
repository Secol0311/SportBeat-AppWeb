package com.sportbeat.gateway.service;

import com.sportbeat.gateway.dto.LigaDTO;
import com.sportbeat.gateway.serviceclient.LigaServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LigaService {

    @Autowired
    private LigaServiceClient ligaServiceClient;

    public Flux<LigaDTO> getAllLigas() {
        return ligaServiceClient.findAllLigas();
    }

    public Mono<LigaDTO> getLigaById(Long id) {
        return ligaServiceClient.findLigaById(id);
    }
}