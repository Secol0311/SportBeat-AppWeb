-- =====================================================
-- MICROSERVICIO: Gestión de Resultados y Estadísticas (Versión Mejorada)
-- Motor: MySQL 8.0+
-- =====================================================

CREATE DATABASE resultados_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE resultados_db;

-- =====================================================
-- TABLA 1: Resultados de Partidos
-- =====================================================

CREATE TABLE resultados (
    id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())), -- Cambio a UUID

    partido_id BINARY(16) NOT NULL UNIQUE, -- Referencia lógica a MS Partidos. UNIQUE para asegurar un solo resultado por partido.
    
    goles_local INT NOT NULL DEFAULT 0,
    goles_visitante INT NOT NULL DEFAULT 0,

    -- Buenas prácticas: saber quién registró el resultado y cuándo.
    registrado_por_usuario_id BINARY(16), -- Referencia lógica a MS Usuarios
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- TABLA 2: Estadísticas de Jugadores POR PARTIDO
-- =====================================================
-- Esta tabla reemplaza a la anterior y es la forma correcta de modelar las estadísticas.

CREATE TABLE estadisticas_jugador_partido (
    id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())),

    partido_id BINARY(16) NOT NULL, -- Referencia lógica a MS Partidos
    jugador_id BINARY(16) NOT NULL, -- Referencia lógica a MS Equipos (tabla jugadores)

    goles INT NOT NULL DEFAULT 0,
    asistencias INT NOT NULL DEFAULT 0,
    tarjetas_amarillas INT NOT NULL DEFAULT 0,
    tarjetas_rojas INT NOT NULL DEFAULT 0,
    minutos_jugados INT, -- Opcional, pero muy útil

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- ÍNDICES SUGERIDOS (Esenciales para el rendimiento)
-- =====================================================

-- Índice para buscar el resultado de un partido específico
CREATE INDEX idx_resultados_partido_id ON resultados(partido_id);

-- Índice compuesto para obtener todas las estadísticas de un partido
CREATE INDEX idx_estadisticas_partido ON estadisticas_jugador_partido(partido_id);

-- Índice para obtener el historial de estadísticas de un jugador
CREATE INDEX idx_estadisticas_jugador ON estadisticas_jugador_partido(jugador_id);


-- ¡ESTO ES UNA VISTA O UNA CONSULTA, NO UNA TABLA!
-- Tu aplicación generaría y ejecutaría este SQL dinámicamente.

-- ¡ESTO ES UNA VISTA O UNA CONSULTA, NO UNA TABLA!
-- Tu aplicación generaría y ejecutaría este SQL dinámicamente.

-- NOTA: Esta consulta asume que todas las tablas están en la misma base de datos.
-- Es un ejemplo para entender la lógica, NO para implementarse en un microservicio.

-- VERSIÓN CORREGIDA Y EXPLÍCITA

SELECT
    e.name AS nombre_equipo,
    COUNT(m.id) AS partidos_jugados,
    SUM(
        CASE
            -- Gana como local
            WHEN m.equipo_local_id = e.id AND r.goles_local > r.goles_visitante THEN 3
            -- Gana como visitante
            WHEN m.equipo_visitante_id = e.id AND r.goles_visitante > r.goles_local THEN 3
            -- Empata
            WHEN r.goles_local = r.goles_visitante THEN 1
            -- Pierde
            ELSE 0
        END
    ) AS puntos,
    SUM(
        CASE
            WHEN m.equipo_local_id = e.id AND r.goles_local > r.goles_visitante THEN 1
            WHEN m.equipo_visitante_id = e.id AND r.goles_visitante > r.goles_local THEN 1
            ELSE 0
        END
    ) AS ganados,
    SUM(CASE WHEN r.goles_local = r.goles_visitante THEN 1 ELSE 0 END) AS empatados,
    SUM(
        CASE
            WHEN m.equipo_local_id = e.id AND r.goles_local < r.goles_visitante THEN 1
            WHEN m.equipo_visitante_id = e.id AND r.goles_visitante < r.goles_local THEN 1
            ELSE 0
        END
    ) AS perdidos,
    SUM(CASE WHEN m.equipo_local_id = e.id THEN r.goles_local ELSE r.goles_visitante END) AS goles_favor,
    SUM(CASE WHEN m.equipo_local_id = e.id THEN r.goles_visitante ELSE r.goles_local END) AS goles_contra,
    (SUM(CASE WHEN m.equipo_local_id = e.id THEN r.goles_local ELSE r.goles_visitante END) -
     SUM(CASE WHEN m.equipo_local_id = e.id THEN r.goles_visitante ELSE r.goles_local END)) AS diferencia_gol
FROM
    equipos_db.equipos e  -- <-- EXPLÍCITO: BD.equipos
LEFT JOIN
    partidos_db.partidos m ON e.id = m.equipo_local_id OR e.id = m.equipo_visitante_id  -- <-- EXPLÍCITO
LEFT JOIN
    resultados_db.resultados r ON m.id = r.partido_id  -- <-- EXPLÍCITO
WHERE
    e.league_id = 'aqui-va-el-uuid-de-la-liga' -- Reemplaza con un UUID real de tu tabla ligas
GROUP BY
    e.id, e.name
ORDER BY
    puntos DESC,
    diferencia_gol DESC,
    goles_favor DESC
LIMIT 0, 1000;


-- ¡ESTO TAMBIÉN ES UNA CONSULTA, NO UN DATO GUARDADO!
SELECT jugador_id, SUM(goles) AS total_goles
FROM estadisticas_jugador_partido
WHERE jugador_id = 'UUID_DEL_JUGADOR'
GROUP BY jugador_id;