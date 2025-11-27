package com.sportbeat.notificacion_service.repository;
import com.sportbeat.notificacion_service.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;


public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByUsuarioIdOrderByCreatedAtDesc(UUID usuarioId);
}