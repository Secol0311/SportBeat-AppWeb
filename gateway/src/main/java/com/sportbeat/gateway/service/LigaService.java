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

/**
 * Servicio de orquestación en el gateway para las operaciones de ligas.
 * Actúa como un intermediario entre los controladores del gateway y el LigaServiceClient.
 */
@Service
public class LigaService {

    @Autowired
    private LigaServiceClient ligaServiceClient;

    public List<LigaDTO> findAllLigas() {
        return ligaServiceClient.findAllLigas().collectList().block();
    }

    public LigaDTO getLigaById(UUID id) {
        return ligaServiceClient.findLigaById(id).block();
    }

    public LigaDTO crearLiga(CrearLigaRequest request) {
        return ligaServiceClient.crearLiga(request).block();
    }

    public LigaDTO actualizarLiga(UUID id, CrearLigaRequest request) {
        return ligaServiceClient.actualizarLiga(id, request).block();
    }

    public void eliminarLiga(UUID id) {
        ligaServiceClient.eliminarLiga(id).block(); // Bloqueamos hasta que la eliminación se complete
    }
}