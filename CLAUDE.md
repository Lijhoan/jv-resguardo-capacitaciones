# Proyecto

**Nombre:** SIGECAP J&V

**Objetivo:** Sistema de escritorio en Java para gestionar capacitaciones del personal de J&V Resguardo.

# Stack obligatorio

- Java 21 LTS
- Maven Wrapper
- MySQL 8.x
- JDBC
- JUnit 5
- Visual Studio Code
- Aplicación de escritorio
- No usar tecnologías web
- No usar Spring Boot
- No usar React, Next.js, Node.js ni TypeScript

# Arquitectura

Mantener una estructura simple:

- app
- config
- model
- repository
- service
- controller
- view

No agregar capas adicionales sin necesidad.

# Principios de desarrollo

- Código simple y educativo.
- Evitar sobreingeniería.
- No crear DTOs, factories, interfaces o patrones innecesarios.
- No agregar dependencias sin justificación.
- Mantener clases pequeñas y entendibles.
- Comentarios cortos y útiles.
- Relacionar las clases o funcionalidades con las historias de usuario cuando corresponda.

Ejemplo de comentario:

```java
/**
 * HU-JVR-001
 * Gestión del registro de trabajadores.
 */
```

# Git

- `main` es la rama estable.
- No desarrollar funcionalidades directamente en `main`.
- Cada módulo se trabaja en una rama `feature/...`.
- Antes de trabajar:
  1. actualizar `main`
  2. crear o cambiar a la rama asignada
- No hacer merge automáticamente.
- No hacer push a `main` sin indicación.
- No modificar módulos ajenos sin necesidad.

# Módulos del sistema

- Login
- Usuarios y roles
- Personal
- Cursos
- Programación
- Participantes
- Asistencia
- Resultados
- Certificados
- Historial
- Vigencias
- Alertas
- Reportes
- Dashboard

# Base de datos

- MySQL
- JDBC
- Base prevista: `sigecap_jv`
- No hardcodear credenciales.
- Usar variables de entorno o configuración local no versionada.

# Calidad

Antes de dar por terminado un cambio:

- ejecutar tests
- ejecutar build
- revisar `git diff --check`
- revisar `git status`
- no dejar errores
- no dejar archivos temporales
- no versionar credenciales

# Interfaz

- Aplicación desktop.
- La UI debe ser simple.
- Los mockups del documento son referencia funcional, no una obligación pixel-perfect.
- Mantener una ventana principal con vistas integradas.
