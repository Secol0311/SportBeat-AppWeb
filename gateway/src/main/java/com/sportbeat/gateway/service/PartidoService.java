package com.sportbeat.gateway.service;

import com.sportbeat.gateway.dto.PartidoDTO;
import com.sportbeat.gateway.serviceclient.PartidoServiceClient;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class PartidoService {

    @Autowired
    private PartidoServiceClient partidoServiceClient;

    public Flux<PartidoDTO> getCalendarioByLiga(UUID idLiga) {
        return partidoServiceClient.findCalendarioByLiga(idLiga);
    }

    public Mono<PartidoDTO> getDetallePartido(UUID id) {
        return partidoServiceClient.findPartidoById(id);
    }
}