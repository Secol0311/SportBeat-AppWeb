package com.sportbeat.notificacion_service.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;




@Entity
@Table(name = "notifications")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {
    @Id
    private UUID id;
    @Column(name = "usuario_id", nullable = false)
    private UUID usuarioId;
    @Enumerated(EnumType.STRING)
    private TipoNotificacion type;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String message;
    @Column(name = "is_read",nullable = false  )
    private Boolean isRead = false;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "sent_at")
    private LocalDateTime sentAt;
    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); }
}