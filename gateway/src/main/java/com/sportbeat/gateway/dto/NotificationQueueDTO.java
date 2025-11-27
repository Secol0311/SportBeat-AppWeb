package com.sportbeat.gateway.dto;

import com.sportbeat.gateway.model.EstadoNotificacion;
import com.sportbeat.gateway.model.TipoNotificacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para representar una entrada en la cola de notificaciones,
 * consumido desde notificaciones-service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationQueueDTO {
    private UUID id;
    private UUID usuarioId;
    private TipoNotificacion type;
    private String title;
    private String message;
    private EstadoNotificacion status;
    private LocalDateTime scheduledFor;
    private Integer attempts;
    private LocalDateTime lastAttemptAt;
    private LocalDateTime processedAt;
    private LocalDateTime createdAt;
}