package com.sportbeat.notificacion_service.repository;
import com.sportbeat.notificacion_service.model.NotificationQueue;
import com.sportbeat.notificacion_service.model.EstadoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public interface NotificationQueueRepository extends JpaRepository<NotificationQueue, UUID> {
    // Encuentra las notificaciones pendientes que ya deben ser enviadas
    List<NotificationQueue> findByStatusAndScheduledForBeforeOrderByCreatedAtAsc(
        EstadoNotificacion status, LocalDateTime dateTime
    );
}