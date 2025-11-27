package com.sportbeat.notificacion_service.service;

import com.sportbeat.notificacion_service.model.*;
import com.sportbeat.notificacion_service.repository.NotificationQueueRepository;
import com.sportbeat.notificacion_service.repository.NotificationRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotificationQueueProcessor {

    private static final Logger logger = LoggerFactory.getLogger(NotificationQueueProcessor.class);
    private static final int MAX_ATTEMPTS = 3;

    @Autowired
    private NotificationQueueRepository queueRepository;

    @Autowired
    private NotificationRepository historyRepository;

    // Se ejecuta cada 15 segundos (15000 milisegundos)
    @Scheduled(fixedRate = 15000)
    @Transactional // Asegura que la operación sea atómica
    public void processQueue() {
        logger.info("Buscando notificaciones pendientes en la cola...");
        List<NotificationQueue> pendingNotifications = queueRepository
                .findByStatusAndScheduledForBeforeOrderByCreatedAtAsc(
                        EstadoNotificacion.PENDING, LocalDateTime.now()
                );

        if (pendingNotifications.isEmpty()) {
            logger.info("No hay notificaciones pendientes.");
            return;
        }

        logger.info("Se encontraron {} notificaciones pendientes.", pendingNotifications.size());

        for (NotificationQueue queueItem : pendingNotifications) {
            try {
                // 1. Marcar como procesando para que otros workers no la tomen
                queueItem.setStatus(EstadoNotificacion.PROCESSING);
                queueRepository.save(queueItem);

                // 2. Simular el envío (aquí iría la lógica de email, SMS, etc.)
                logger.info("Enviando notificación ID: {} al usuario: {}", queueItem.getId(), queueItem.getUsuarioId());
                simulateSending(queueItem);

                // 3. Si el envío es exitoso, mover al historial
                Notification historyRecord = Notification.builder()
                        .usuarioId(queueItem.getUsuarioId())
                        .type(queueItem.getType())
                        .title(queueItem.getTitle())
                        .message(queueItem.getMessage())
                        .sentAt(LocalDateTime.now())
                        .build();
                historyRepository.save(historyRecord);

                // 4. Marcar como enviada en la cola
                queueItem.setStatus(EstadoNotificacion.SENT);
                queueItem.setProcessedAt(LocalDateTime.now());
                queueRepository.save(queueItem);

            } catch (Exception e) {
                logger.error("Fallo al enviar notificación ID: {}", queueItem.getId(), e);
                queueItem.setAttempts(queueItem.getAttempts() + 1);
                queueItem.setLastAttemptAt(LocalDateTime.now());

                if (queueItem.getAttempts() >= MAX_ATTEMPTS) {
                    queueItem.setStatus(EstadoNotificacion.FAILED);
                    queueItem.setProcessedAt(LocalDateTime.now());
                } else {
                    queueItem.setStatus(EstadoNotificacion.PENDING); // Volver a la cola para reintentar
                }
                queueRepository.save(queueItem);
            }
        }
    }

    private void simulateSending(NotificationQueue queueItem) {
        // Simula un retardo y un posible fallo
        if (Math.random() > 0.95) { // 5% de probabilidad de fallo para probar reintentos
            throw new RuntimeException("Fallo simulado en el envío.");
        }
        logger.info("Notificación enviada (simuladamente) con éxito: '{}'", queueItem.getTitle());
    }
}