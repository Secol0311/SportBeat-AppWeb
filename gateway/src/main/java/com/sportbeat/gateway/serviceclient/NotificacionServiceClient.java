package com.sportbeat.gateway.serviceclient;

import com.sportbeat.gateway.dto.CrearNotificacionRequestDTO;
import com.sportbeat.gateway.dto.NotificationDTO;
import com.sportbeat.gateway.dto.NotificationQueueDTO;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class NotificacionServiceClient {

    private final WebClient webClient;
    private final String notificacionServiceUrl;

    public NotificacionServiceClient(WebClient.Builder webClientBuilder, @Value("${urls.servicios.notificacion}") String notificacionServiceUrl) {
        this.webClient = webClientBuilder.build();
        this.notificacionServiceUrl = notificacionServiceUrl;
    }

    public Mono<NotificationQueueDTO> crearNotificacion(CrearNotificacionRequestDTO request) {
        return this.webClient.post()
                .uri(notificacionServiceUrl + "/api/notifications")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(NotificationQueueDTO.class);
    }

    public Flux<NotificationDTO> obtenerHistorialPorUsuario(UUID usuarioId) {
        return this.webClient.get()
                .uri(notificacionServiceUrl + "/api/notifications/usuario/{usuarioId}", usuarioId)
                .retrieve()
                .bodyToFlux(NotificationDTO.class);
    }
}