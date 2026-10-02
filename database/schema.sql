-- HU-JVR-012 / HU-JVR-013
-- Esquema inicial: roles y usuarios para gestion de usuarios y autenticacion.
-- Compatible con MySQL 8.x. Ejecutar sobre la base sigecap_jv.

CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(150),
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    nombre_completo VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol_id BIGINT NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_usuarios_rol FOREIGN KEY (rol_id) REFERENCES roles(id)
);

-- Datos de ejemplo minimos y ficticios, sin contraseñas reales.
INSERT INTO roles (nombre, descripcion, estado) VALUES
    ('ADMIN', 'Administrador del sistema', TRUE),
    ('SUPERVISOR', 'Supervisor de operaciones', TRUE);
