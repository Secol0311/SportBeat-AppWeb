package com.sportbeat.notificacion_service.service;
import com.sportbeat.notificacion_service.model.Notification;
import com.sportbeat.notificacion_service.model.EstadoNotificacion;
import com.sportbeat.notificacion_service.model.NotificationQueue;
import com.sportbeat.notificacion_service.model.TipoNotificacion;
import com.sportbeat.notificacion_service.repository.NotificationQueueRepository;
import com.sportbeat.notificacion_service.repository.NotificationRepository;
import com.sportbeat.notificacion_service.dto.CrearNotificacionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {
    @Autowired private NotificationQueueRepository queueRepository;
    @Autowired private NotificationRepository historyRepository;

    public NotificationQueue crearNotificacion(CrearNotificacionRequest request) {
        NotificationQueue nuevaNotificacion = NotificationQueue.builder()
                .usuarioId(request.getUsuarioId())
                .type(request.getType())
                .title(request.getTitle())
                .message(request.getMessage())
                .scheduledFor(LocalDateTime.now()) // Se programa para "ahora"
                .build();
        return queueRepository.save(nuevaNotificacion);
    }

    public List<Notification> obtenerHistorialPorUsuario(UUID usuarioId) {
        return historyRepository.findByUsuarioIdOrderByCreatedAtDesc(usuarioId);
    }

    public void marcarComoLeida(UUID notificationId) {
        historyRepository.findById(notificationId).ifPresent(notificacion -> {
            notificacion.setIsRead(true);
            historyRepository.save(notificacion);
        });
    }
}