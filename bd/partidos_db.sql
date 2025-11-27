-- =====================================================
-- MICROSERVICIO: Gestión de Partidos (Versión Mejorada)
-- Motor: MySQL 8.0+
-- =====================================================

CREATE DATABASE partidos_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE partidos_db;

-- =====================================================
-- TABLA PRINCIPAL
-- =====================================================

CREATE TABLE partidos (
    id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())), -- Cambio a UUID

    -- Estas columnas son REFERENCIAS LÓGICAS a otros microservicios.
    -- NO hay claves foráneas físicas para mantener el desacoplamiento.
    -- La aplicación se encarga de validar que estos IDs existen.
    liga_id BINARY(16) NOT NULL,
    equipo_local_id BINARY(16) NOT NULL,
    equipo_visitante_id BINARY(16) NOT NULL,

    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    venue VARCHAR(100), -- 'venue' es un término más estándar que 'estadio'

    -- ENUM más completo para cubrir todos los casos de uso.
    estado ENUM('PROGRAMADO', 'SUSPENDIDO', 'CANCELADO', 'EN_JUEGO', 'FINALIZADO') DEFAULT 'PROGRAMADO',

    -- Campos de auditoría para consistencia
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- =====================================================
-- ÍNDICES SUGERIDOS (Cruciales para el rendimiento)
-- =====================================================

-- Índice para buscar todos los partidos de una liga específico (muy común)
CREATE INDEX idx_partidos_liga_id ON partidos(liga_id);

-- Índice para buscar los partidos de un equipo (como local o visitante)
CREATE INDEX idx_partidos_equipo_local ON partidos(equipo_local_id);
CREATE INDEX idx_partidos_equipo_visitante ON partidos(equipo_visitante_id);

-- Índice compuesto para obtener el calendario de un equipo en una liga
CREATE INDEX idx_partidos_liga_equipo ON partidos(liga_id, equipo_local_id);
CREATE INDEX idx_partidos_liga_equipo_visitante ON partidos(liga_id, equipo_visitante_id);

-- Índice para buscar por fecha y estado
CREATE INDEX idx_partidos_fecha_estado ON partidos(fecha, estado);
