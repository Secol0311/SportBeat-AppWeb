package com.sportbeat.gateway.service;

import com.sportbeat.gateway.dto.CrearResultadoRequest;
import com.sportbeat.gateway.dto.ResultadoDTO;
import com.sportbeat.gateway.serviceclient.ResultadoServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Servicio de orquestación en el gateway para las operaciones de resultados.
 */
@Service
public class ResultadoService {

    @Autowired
    private ResultadoServiceClient resultadoServiceClient;

    public Mono<ResultadoDTO> crearResultado(CrearResultadoRequest request) {
        return resultadoServiceClient.crearResultado(request);
    }

    public Mono<ResultadoDTO> obtenerResultadoPorPartidoId(UUID partidoId) {
        return resultadoServiceClient.obtenerResultadoPorPartidoId(partidoId);
    }
}