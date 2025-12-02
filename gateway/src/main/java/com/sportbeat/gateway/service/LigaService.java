// En: gateway/src/main/java/com/sportbeat/gateway/service/LigaService.java
package com.sportbeat.gateway.service;

import com.sportbeat.gateway.dto.CrearLigaRequest;
import com.sportbeat.gateway.dto.LigaDTO;
import com.sportbeat.gateway.serviceclient.LigaServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
public class LigaService {

    @Autowired
    private LigaServiceClient ligaServiceClient;

    public Mono<List<LigaDTO>> findAllLigas() {
        // Transformamos el Flux a un Mono<Lista> para que sea más fácil de manejar en el controlador
        return ligaServiceClient.findAllLigas().collectList();
    }

    public Mono<LigaDTO> getLigaById(UUID id) {
        return ligaServiceClient.findLigaById(id);
    }

    public Mono<LigaDTO> crearLiga(CrearLigaRequest request) {
        return ligaServiceClient.crearLiga(request);
    }

    public Mono<LigaDTO> actualizarLiga(UUID id, CrearLigaRequest request) {
        return ligaServiceClient.actualizarLiga(id, request);
    }

    public Mono<Void> eliminarLiga(UUID id) {
        return ligaServiceClient.eliminarLiga(id);
    }
}