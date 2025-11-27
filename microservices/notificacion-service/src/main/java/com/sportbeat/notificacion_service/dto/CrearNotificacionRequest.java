package com.sportbeat.notificacion_service.dto;
import com.sportbeat.notificacion_service.model.TipoNotificacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class CrearNotificacionRequest {
    @NotNull(message = "El ID del usuario es obligatorio")
    private UUID usuarioId;
    @NotNull(message = "El tipo de notificación es obligatorio")
    private TipoNotificacion type;
    @NotBlank(message = "El título es obligatorio")
    private String title;
    @NotBlank(message = "El mensaje es obligatorio")
    private String message;
}