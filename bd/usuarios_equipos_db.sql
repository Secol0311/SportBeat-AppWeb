-- =====================================================
-- MICROSERVICIO: Gestión de Usuarios y Equipos (Versión Mejorada)
-- Motor: MySQL 8.0+
-- =====================================================

CREATE DATABASE equipos_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE equipos_db;

-- =====================================================
-- TABLAS PRINCIPALES
-- =====================================================

-- Ligas -------------------------------------------------
-- Define las competiciones.
CREATE TABLE ligas (
  id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())), -- Cambio a UUID
  league_code VARCHAR(20) NOT NULL UNIQUE,
  name VARCHAR(100) NOT NULL,
  sport VARCHAR(30) NOT NULL, -- 'sport' es más estándar que 'deporte'
  season VARCHAR(30) NOT NULL,
  admin_id BINARY(16), -- FK hacia usuarios
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Usuarios ------------------------------------------------
-- Tabla central para todos los actores del sistema.
CREATE TABLE usuarios (
  id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())), -- Cambio a UUID
  username VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  telefono VARCHAR(20) NOT NULL UNIQUE,
  first_name VARCHAR(50) NOT NULL, -- Movido desde jugadores/entrenadores
  last_name VARCHAR(50) NOT NULL,  -- Movido desde jugadores/entrenadores
  role ENUM('PLAYER','COACH','ADMIN') NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Equipos -------------------------------------------------
CREATE TABLE equipos (
  id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())), -- Cambio a UUID
  name VARCHAR(100) NOT NULL,
  district VARCHAR(100),
  department VARCHAR(100),
  league_id BINARY(16), -- ¡NUEVO! FK hacia ligas
  coach_id BINARY(16), -- FK hacia usuarios (rol COACH)
  founded_year YEAR,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Jugadores ----------------------------------------------
-- Contiene solo la información específica del rol de jugador.
CREATE TABLE jugadores (
  id BINARY(16) PRIMARY KEY DEFAULT (UUID_TO_BIN(UUID())), -- Cambio a UUID
  usuario_id BINARY(16) NOT NULL, -- FK hacia usuarios (rol PLAYER)
  team_id BINARY(16), -- ¡NUEVO! FK hacia equipos (un jugador puede estar sin equipo)
  jersey_number INT,
  position VARCHAR(30),
  height_cm INT,
  weight_kg INT,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- =====================================================
-- DEFINICIÓN DE CLAVES FORÁNEAS
-- =====================================================

-- ligas.admin_id -> usuarios.id
ALTER TABLE ligas
  ADD CONSTRAINT fk_ligas_admin
  FOREIGN KEY (admin_id) REFERENCES usuarios(id)
  ON DELETE SET NULL ON UPDATE CASCADE;

-- equipos.league_id -> ligas.id
ALTER TABLE equipos
  ADD CONSTRAINT fk_equipos_liga
  FOREIGN KEY (league_id) REFERENCES ligas(id)
  ON DELETE RESTRICT ON UPDATE CASCADE; -- No se puede borrar una liga si tiene equipos

-- equipos.coach_id -> usuarios.id
ALTER TABLE equipos
  ADD CONSTRAINT fk_equipos_entrenador
  FOREIGN KEY (coach_id) REFERENCES usuarios(id)
  ON DELETE SET NULL ON UPDATE CASCADE; -- Si se borra el usuario (entrenador), el equipo se queda sin uno.

-- jugadores.usuario_id -> usuarios.id
ALTER TABLE jugadores
  ADD CONSTRAINT fk_jugadores_usuario
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
  ON DELETE CASCADE ON UPDATE CASCADE; -- Si se borra el usuario, su perfil de jugador también.

-- jugadores.team_id -> equipos.id
ALTER TABLE jugadores
  ADD CONSTRAINT fk_jugadores_equipo
  FOREIGN KEY (team_id) REFERENCES equipos(id)
  ON DELETE SET NULL ON UPDATE CASCADE; -- Si se borra un equipo, los jugadores quedan sin equipo.

-- =====================================================
-- ÍNDICES SUGERIDOS (Para mejorar el rendimiento)
-- =====================================================

CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_usuarios_role ON usuarios(role);
CREATE INDEX idx_equipos_league_id ON equipos(league_id);
CREATE INDEX idx_jugadores_usuario_id ON jugadores(usuario_id);
CREATE INDEX idx_jugadores_team_id ON jugadores(team_id);
