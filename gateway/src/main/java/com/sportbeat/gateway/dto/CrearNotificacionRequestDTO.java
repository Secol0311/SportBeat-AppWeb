package com.sportbeat.gateway.dto;
import com.sportbeat.gateway.model.TipoNotificacion;
import lombok.Data;
import java.util.UUID;
@Data
public class CrearNotificacionRequestDTO {
    private UUID usuarioId;
    private TipoNotificacion type;
    private String title;
    private String message;
}