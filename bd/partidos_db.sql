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
-- PARTIDOS LIGA PERUANA DE FÚTBOL (LPF-2025) -> league_id bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb
-- =====================================================
INSERT INTO partidos (id, liga_id, equipo_local_id, equipo_visitante_id, fecha, hora, venue, estado)
VALUES
-- Jornada 1
(UUID_TO_BIN('p1111111-1111-1111-1111-111111111111'),
 UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
 UUID_TO_BIN('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'), -- Club Deportivo Lima Norte
 UUID_TO_BIN('ffffffff-ffff-ffff-ffff-ffffffffffff'), -- Atlético Sur Chico
 '2025-03-01', '15:00:00', 'Estadio IPD - Lima Norte', 'PROGRAMADO'),

(UUID_TO_BIN('p1111111-2222-2222-2222-222222222222'),
 UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
 UUID_TO_BIN('10101010-1010-1010-1010-101010101010'), -- Sport Lima Central
 UUID_TO_BIN('20202020-2020-2020-2020-202020202020'), -- Deportivo Arequipa
 '2025-03-02', '16:00:00', 'Estadio Nacional', 'PROGRAMADO'),

-- Jornada 2
(UUID_TO_BIN('p1111111-3333-3333-3333-333333333333'),
 UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
 UUID_TO_BIN('30303030-3030-3030-3030-303030303030'), -- Cusco United
 UUID_TO_BIN('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'),
 '2025-03-08', '14:00:00', 'Estadio Garcilaso - Cusco', 'PROGRAMADO'),

(UUID_TO_BIN('p1111111-4444-4444-4444-444444444444'),
 UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
 UUID_TO_BIN('ffffffff-ffff-ffff-ffff-ffffffffffff'),
 UUID_TO_BIN('10101010-1010-1010-1010-101010101010'),
 '2025-03-09', '17:00:00', 'Estadio Municipal - Sur Chico', 'PROGRAMADO');

-- =====================================================
-- PARTIDOS LIGA PERUANA DE VÓLEY (LPV-2025) -> league_id cccccccc-cccc-cccc-cccc-cccccccccccc
-- =====================================================
INSERT INTO partidos (id, liga_id, equipo_local_id, equipo_visitante_id, fecha, hora, venue, estado)
VALUES
-- Jornada 1
(UUID_TO_BIN('11111111-aaaa-4aaa-bbbb-111111111111'),
 UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
 UUID_TO_BIN('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'), -- Club Deportivo Lima Norte
 UUID_TO_BIN('ffffffff-ffff-ffff-ffff-ffffffffffff'), -- Atlético Sur Chico
 '2025-03-01', '15:00:00', 'Estadio IPD - Lima Norte', 'PROGRAMADO'),

(UUID_TO_BIN('22222222-aaaa-4aaa-bbbb-222222222222'),
 UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
 UUID_TO_BIN('10101010-1010-1010-1010-101010101010'), -- Sport Lima Central
 UUID_TO_BIN('20202020-2020-2020-2020-202020202020'), -- Deportivo Arequipa
 '2025-03-02', '16:00:00', 'Estadio Nacional', 'PROGRAMADO'),

-- Jornada 2
(UUID_TO_BIN('33333333-aaaa-4aaa-bbbb-333333333333'),
 UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
 UUID_TO_BIN('30303030-3030-3030-3030-303030303030'), -- Cusco United
 UUID_TO_BIN('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'),
 '2025-03-08', '14:00:00', 'Estadio Garcilaso - Cusco', 'PROGRAMADO'),

(UUID_TO_BIN('44444444-aaaa-4aaa-bbbb-444444444444'),
 UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
 UUID_TO_BIN('ffffffff-ffff-ffff-ffff-ffffffffffff'),
 UUID_TO_BIN('10101010-1010-1010-1010-101010101010'),
 '2025-03-09', '17:00:00', 'Estadio Municipal - Sur Chico', 'PROGRAMADO');


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


select*from partidos