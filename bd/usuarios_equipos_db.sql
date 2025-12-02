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


-- Inserts:
-- =====================================================
-- USUARIOS
-- =====================================================

INSERT INTO usuarios (id, username, password_hash, email, telefono, first_name, last_name, role)
VALUES

(UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), 'admin_sportbeat', '$2a$10$ABC', 'admin@sportbeat.pe', '999111222',
 'Carlos', 'Ramírez', 'ADMIN'),
(UUID_TO_BIN('22222222-2222-2222-2222-222222222222'), 'coach_futbol', '$2a$10$ABC', 'jhuaman@ipd.gob.pe', '987654321',
 'Juan', 'Huamán', 'COACH'),
(UUID_TO_BIN('33333333-3333-3333-3333-333333333333'), 'coach_voley', '$2a$10$ABC', 'mcontreras@ipd.gob.pe', '988776655',
 'María', 'Contreras', 'COACH'),
(UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), 'coach_basket', '$2a$10$ABC', 'lrios@ipd.gob.pe', '912345678',
 'Luis', 'Ríos', 'COACH'),
(UUID_TO_BIN('55555555-5555-5555-5555-555555555555'), 'jtorres10', '$2a$10$ABC', 'jorge.torres@gmail.com', '954111222',
 'Jorge', 'Torres', 'PLAYER'),
(UUID_TO_BIN('66666666-6666-6666-6666-666666666666'), 'arodriguez9', '$2a$10$ABC', 'alexis.rod@gmail.com', '954333444',
 'Alexis', 'Rodríguez', 'PLAYER'),
(UUID_TO_BIN('77777777-7777-7777-7777-777777777777'), 'lvalverde', '$2a$10$ABC', 'luisa.valverde@gmail.com', '955111222',
 'Luisa', 'Valverde', 'PLAYER'),
(UUID_TO_BIN('88888888-8888-8888-8888-888888888888'), 'cchavez', '$2a$10$ABC', 'carla.chavez@gmail.com', '955333444',
 'Carla', 'Chávez', 'PLAYER'),
(UUID_TO_BIN('99999999-9999-9999-9999-999999999999'), 'mquispe', '$2a$10$ABC', 'mateo.quispe@gmail.com', '956111222',
 'Mateo', 'Quispe', 'PLAYER'),
(UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), 'rvasquez', '$2a$10$ABC', 'raul.vasquez@gmail.com', '956333444',
 'Raúl', 'Vásquez', 'PLAYER'),
(UUID_TO_BIN('bbbbbbbb-bb11-bb11-bb11-bbbbbbbbbb01'), 'coach_lima_uno', '$2a$10$ABC', 'cgarcia@ipd.gob.pe', '912000111', 'César', 'García', 'COACH'),
(UUID_TO_BIN('bbbbbbbb-bb22-bb22-bb22-bbbbbbbbbb02'), 'coach_arequipa', '$2a$10$ABC', 'mmendoza@ipd.gob.pe', '912000222', 'Marcos', 'Mendoza', 'COACH'),
(UUID_TO_BIN('bbbbbbbb-bb33-bb33-bb33-bbbbbbbbbb03'), 'coach_cusco', '$2a$10$ABC', 'apaz@ipd.gob.pe', '912000333', 'Ana', 'Paz', 'COACH'),
(UUID_TO_BIN('77770000-7777-7777-7777-777777777700'), 'dcastillo', '$2a$10$ABC', 'diego.castillo@gmail.com', '959000111', 'Diego', 'Castillo', 'PLAYER'),
(UUID_TO_BIN('88880000-8888-8888-8888-888888888800'), 'srodriguez', '$2a$10$ABC', 'silvia.rodriguez@gmail.com', '959000222', 'Silvia', 'Rodríguez', 'PLAYER'),
(UUID_TO_BIN('99990000-9999-9999-9999-999999999900'), 'fquispe', '$2a$10$ABC', 'fabian.quispe@gmail.com', '959000333', 'Fabián', 'Quispe', 'PLAYER');
 
 -- =====================================================
-- LIGAS
-- =====================================================

INSERT INTO ligas (id, league_code, name, sport, season, admin_id)
VALUES
(UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'), 'LPF-2025', 'Liga Peruana de Fútbol', 'FUTBOL', '2025',
 UUID_TO_BIN('11111111-1111-1111-1111-111111111111')),

(UUID_TO_BIN('cccccccc-cccc-cccc-cccc-cccccccccccc'), 'LPV-2025', 'Liga Peruana de Vóley', 'VOLEY', '2025',
 UUID_TO_BIN('11111111-1111-1111-1111-111111111111')),

(UUID_TO_BIN('dddddddd-dddd-dddd-dddd-dddddddddddd'), 'LPB-2025', 'Liga Peruana de Basketball', 'BASKETBALL', '2025',
 UUID_TO_BIN('11111111-1111-1111-1111-111111111111'));
 
 -- =====================================================
-- EQUIPOS
-- =====================================================

INSERT INTO equipos (id, name, district, department, league_id, coach_id, founded_year)
VALUES
-- FUTBOL
(UUID_TO_BIN('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'), 'Club Deportivo Lima Norte', 'Los Olivos', 'Lima',
 UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
 UUID_TO_BIN('22222222-2222-2222-2222-222222222222'), 2005),

(UUID_TO_BIN('ffffffff-ffff-ffff-ffff-ffffffffffff'), 'Atlético Sur Chico', 'San Juan de Miraflores', 'Lima',
 UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
 UUID_TO_BIN('22222222-2222-2222-2222-222222222222'), 2010),

-- VOLEY
(UUID_TO_BIN('abababab-abab-abab-abab-abababababab'), 'Vóley Estrellas del Pacífico', 'Miraflores', 'Lima',
 UUID_TO_BIN('cccccccc-cccc-cccc-cccc-cccccccccccc'),
 UUID_TO_BIN('33333333-3333-3333-3333-333333333333'), 2012),

-- BASKET
(UUID_TO_BIN('cdcdcdcd-cdcd-cdcd-cdcd-cdcdcdcdcdcd'), 'Basket Lima Eagles', 'San Borja', 'Lima',
 UUID_TO_BIN('dddddddd-dddd-dddd-dddd-dddddddddddd'),
 UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), 2015),
(UUID_TO_BIN('10101010-1010-1010-1010-101010101010'), 'Sport Lima Central', 'Cercado de Lima', 'Lima',
 UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
 UUID_TO_BIN('bbbbbbbb-bb11-bb11-bb11-bbbbbbbbbb01'), 1998),

(UUID_TO_BIN('20202020-2020-2020-2020-202020202020'), 'Deportivo Arequipa', 'Cayma', 'Arequipa',
 UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
 UUID_TO_BIN('bbbbbbbb-bb22-bb22-bb22-bbbbbbbbbb02'), 2002),

(UUID_TO_BIN('30303030-3030-3030-3030-303030303030'), 'Cusco United', 'Wanchaq', 'Cusco',
 UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'),
 UUID_TO_BIN('bbbbbbbb-bb33-bb33-bb33-bbbbbbbbbb03'), 2011),

-- VOLEY (liga_id = cccccccc-cccc-cccc-cccc-cccccccccccc)
(UUID_TO_BIN('41414141-4141-4141-4141-414141414141'), 'Vóley Lima Warriors', 'San Miguel', 'Lima',
 UUID_TO_BIN('cccccccc-cccc-cccc-cccc-cccccccccccc'),
 UUID_TO_BIN('33333333-3333-3333-3333-333333333333'), 2014),

(UUID_TO_BIN('42424242-4242-4242-4242-424242424242'), 'Vóley Arequipa Stars', 'Cayma', 'Arequipa',
 UUID_TO_BIN('cccccccc-cccc-cccc-cccc-cccccccccccc'),
 UUID_TO_BIN('bbbbbbbb-bb22-bb22-bb22-bbbbbbbbbb02'), 2016),

-- BASKET (liga_id = dddddddd-dddd-dddd-dddd-dddddddddddd)
(UUID_TO_BIN('51515151-5151-5151-5151-515151515151'), 'Basket Arequipa Hawks', 'Cayma', 'Arequipa',
 UUID_TO_BIN('dddddddd-dddd-dddd-dddd-dddddddddddd'),
 UUID_TO_BIN('bbbbbbbb-bb22-bb22-bb22-bbbbbbbbbb02'), 2013),

(UUID_TO_BIN('52525252-5252-5252-5252-525252525252'), 'Cusco Rim Shooters', 'San Sebastián', 'Cusco',
 UUID_TO_BIN('dddddddd-dddd-dddd-dddd-dddddddddddd'),
 UUID_TO_BIN('bbbbbbbb-bb33-bb33-bb33-bbbbbbbbbb03'), 2018);
-- =====================================================
-- JUGADORES
-- =====================================================

INSERT INTO jugadores (id, usuario_id, team_id, jersey_number, position, height_cm, weight_kg)
VALUES
-- FUTBOL
(UUID_TO_BIN('efefefef-efef-efef-efef-efefefefefef'), 
 UUID_TO_BIN('55555555-5555-5555-5555-555555555555'),
 UUID_TO_BIN('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee'),
 10, 'Delantero', 178, 72),

(UUID_TO_BIN('12121212-1212-1212-1212-121212121212'), 
 UUID_TO_BIN('66666666-6666-6666-6666-666666666666'),
 UUID_TO_BIN('ffffffff-ffff-ffff-ffff-ffffffffffff'),
 9, 'Extremo Derecho', 175, 70),

-- VOLEY
(UUID_TO_BIN('34343434-3434-3434-3434-343434343434'), 
 UUID_TO_BIN('77777777-7777-7777-7777-777777777777'),
 UUID_TO_BIN('abababab-abab-abab-abab-abababababab'),
 7, 'Opuesto', 183, 68),

(UUID_TO_BIN('56565656-5656-5656-5656-565656565656'), 
 UUID_TO_BIN('88888888-8888-8888-8888-888888888888'),
 UUID_TO_BIN('abababab-abab-abab-abab-abababababab'),
 3, 'Armadora', 176, 64),

-- BASKET
(UUID_TO_BIN('78787878-7878-7878-7878-787878787878'), 
 UUID_TO_BIN('99999999-9999-9999-9999-999999999999'),
 UUID_TO_BIN('cdcdcdcd-cdcd-cdcd-cdcd-cdcdcdcdcdcd'),
 23, 'Base', 185, 80),

(UUID_TO_BIN('9a9a9a9a-9a9a-9a9a-9a9a-9a9a9a9a9a9a'), 
 UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'),
 UUID_TO_BIN('cdcdcdcd-cdcd-cdcd-cdcd-cdcdcdcdcdcd'),
 14, 'Alero', 192, 87),

(UUID_TO_BIN('a1a1a1a1-a1a1-a1a1-a1a1-a1a1a1a1a1a1'),
 UUID_TO_BIN('77770000-7777-7777-7777-777777777700'),
 UUID_TO_BIN('10101010-1010-1010-1010-101010101010'),
 11, 'Mediocampista', 176, 70),

-- Deportivo Arequipa (futbol)
(UUID_TO_BIN('b2b2b2b2-b2b2-b2b2-b2b2-b2b2b2b2b2b2'),
 UUID_TO_BIN('88880000-8888-8888-8888-888888888800'),
 UUID_TO_BIN('20202020-2020-2020-2020-202020202020'),
 7, 'Defensa', 180, 78),

-- Cusco United (futbol)
(UUID_TO_BIN('c3c3c3c3-c3c3-c3c3-c3c3-c3c3c3c3c3c3'),
 UUID_TO_BIN('99990000-9999-9999-9999-999999999900'),
 UUID_TO_BIN('30303030-3030-3030-3030-303030303030'),
 9, 'Delantero', 182, 76),

-- Vóley Lima Warriors
(UUID_TO_BIN('d4d4d4d4-d4d4-d4d4-d4d4-d4d4d4d4d4d4'),
 UUID_TO_BIN('55555555-5555-5555-5555-555555555555'), -- usa jugador ya existente si aplica
 UUID_TO_BIN('41414141-4141-4141-4141-414141414141'),
 1, 'Opuesto', 185, 75),

-- Vóley Arequipa Stars
(UUID_TO_BIN('e5e5e5e5-e5e5-e5e5-e5e5-e5e5e5e5e5e5'),
 UUID_TO_BIN('66666666-6666-6666-6666-666666666666'), -- usa jugador ya existente si aplica
 UUID_TO_BIN('42424242-4242-4242-4242-424242424242'),
 2, 'Armadora', 174, 64),

-- Basket Arequipa Hawks
(UUID_TO_BIN('f6f6f6f6-f6f6-f6f6-f6f6-f6f6f6f6f6f6'),
 UUID_TO_BIN('99999999-9999-9999-9999-999999999999'),
 UUID_TO_BIN('51515151-5151-5151-5151-515151515151'),
 5, 'Alero', 195, 88);



 
 describe usuarios;
select* from jugadores