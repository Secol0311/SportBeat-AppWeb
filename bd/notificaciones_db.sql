-- =====================================================
-- MICROSERVICIO: Notificaciones (Versión Mejorada)
-- Motor: MySQL 8.0+
-- =====================================================

CREATE DATABASE notificaciones_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE notificaciones_db;

-- =====================================================
-- TABLA 1: Registro de Notificaciones Enviadas
-- =====================================================
-- Esta tabla es el historial de todas las notificaciones que se han generado.

CREATE TABLE notifications (
    id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())), -- Cambio a UUID

    usuario_id BINARY(16) NOT NULL, -- Referencia lógica a MS Usuarios

    -- Usamos ENUM para garantizar la consistencia de los tipos.
    type ENUM('match_reminder', 'result_posted', 'schedule_change', 'league_news') NOT NULL,

    title VARCHAR(255) NOT NULL, -- Título corto para la notificación
    message TEXT NOT NULL,       -- El contenido completo

    is_read BOOLEAN DEFAULT FALSE, -- Cambio de nombre a 'is_read'
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    sent_at DATETIME NULL -- Se registra la fecha real de envío
);

-- =====================================================
-- TABLA 2: Cola de Procesamiento (Patrón Profesional)
-- =====================================================
-- Esta tabla maneja el envío asíncrono de las notificaciones.

CREATE TABLE notification_queue (
    id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),

    usuario_id BINARY(16) NOT NULL,
    type ENUM('match_reminder', 'result_posted', 'schedule_change', 'league_news') NOT NULL,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,

    -- Estados del proceso en la cola
    status ENUM('PENDING', 'PROCESSING', 'SENT', 'FAILED') DEFAULT 'PENDING',

    -- Para programar notificaciones futuras (ej: recordatorio 1 hora antes del partido)
    scheduled_for DATETIME NOT NULL,

    attempts INT DEFAULT 0, -- Número de intentos de envío (para reintentos automáticos)
    last_attempt_at DATETIME NULL,
    processed_at DATETIME NULL, -- Cuando se marcó como SENT o FAILED

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- ÍNDICES SUGERIDOS (Críticos para el rendimiento)
-- =====================================================

-- Índices para la tabla de historial (notifications)
CREATE INDEX idx_notifications_usuario_id ON notifications(usuario_id);
CREATE INDEX idx_notifications_is_read ON notifications(is_read);

-- Índices para la tabla de cola (notification_queue)
-- Muy importante para que el worker encuentre rápidamente las tareas pendientes.
CREATE INDEX idx_queue_status_scheduled ON notification_queue(status, scheduled_for);
CREATE INDEX idx_queue_usuario_id ON notification_queue(usuario_id);
