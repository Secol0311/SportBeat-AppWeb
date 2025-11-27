package com.sportbeat.gateway.dto;
import com.sportbeat.gateway.model.TipoNotificacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
public class NotificationDTO {
    private UUID id;
    private UUID usuarioId;
    private TipoNotificacion type;
    private String title;
    private String message;
    private Boolean isRead;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
}