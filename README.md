# SIGECAP J&V

Sistema de gestión de cursos de capacitación para el personal del área de Operaciones de J&V Resguardo.

## Objetivo

SIGECAP J&V reemplazará el manejo disperso de capacitaciones mediante legajos, carpetas digitales, archivos Excel, correo, llamadas y WhatsApp. El sistema busca centralizar la información, mejorar la trazabilidad y facilitar el seguimiento de cursos, participantes, asistencia, resultados, certificados y vigencias.

## Alcance

La solución será una aplicación de escritorio Java con base de datos relacional MySQL, operaciones CRUD, acceso mediante JDBC y autenticación basada en usuarios, roles y permisos. El proyecto tiene alcance académico y no contempla un despliegue productivo real.

No será una aplicación web o móvil, ni utilizará microservicios, API REST como arquitectura principal, Spring Boot, React, Next.js ni integraciones con WhatsApp, SMS, SUCAMEC o APN. La tecnología de interfaz desktop se decidirá en una etapa posterior; por ahora no se incorpora Swing ni JavaFX.

## Stack técnico

- Java 21 LTS, versión oficial del proyecto.
- Maven con Maven Wrapper 3.9.9.
- MySQL 8.x como gestor de base de datos objetivo.
- MySQL Connector/J y JDBC.
- JUnit 5 para pruebas.
- UTF-8.
- Visual Studio Code como IDE principal.
- Git para control de versiones.
- Arquitectura organizada por capas.

## Arquitectura de carpetas

```text
src/
├── main/
│   ├── java/pe/jvresguardo/sigecap/
│   │   ├── app/          # Punto de entrada y arranque
│   │   ├── config/       # Configuración y futura conexión a BD
│   │   ├── model/        # Entidades del dominio
│   │   ├── repository/   # Persistencia JDBC
│   │   ├── service/      # Reglas de negocio
│   │   ├── controller/   # Coordinación entre vistas y servicios
│   │   └── view/         # Interfaz gráfica desktop futura
│   └── resources/        # Recursos de la aplicación
└── test/java/pe/jvresguardo/sigecap/
```

Las capas están preparadas para crecer sin mezclar responsabilidades. En este bootstrap solo existe el punto de entrada; las demás carpetas se conservan con `.gitkeep` hasta incorporar código real.

## Clonar y requisitos

```bash
git clone <URL-del-repositorio>
cd jv-resguardo-capacitaciones
```

Requisitos mínimos:

- JDK Eclipse Temurin u otra distribución compatible con Java 21 LTS.
- Git.
- Acceso a Internet en la primera ejecución para descargar Maven Wrapper y dependencias.
- MySQL 8.x será necesario cuando se implemente la persistencia, pero no para compilar este bootstrap.

## Compilar, probar y ejecutar

Windows PowerShell:

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd clean package
java -cp target/classes pe.jvresguardo.sigecap.app.SigecapApplication
```

Linux/macOS:

```bash
./mvnw clean test
./mvnw clean package
java -cp target/classes pe.jvresguardo.sigecap.app.SigecapApplication
```

El punto de entrada actual solo confirma que la aplicación inicia correctamente; todavía no construye pantallas.

## Configuración futura de base de datos

La base prevista se denomina `sigecap_jv`. Cuando se implemente la conexión JDBC, se utilizarán variables de entorno o una configuración local no versionada:

```text
DB_HOST
DB_PORT
DB_NAME
DB_USER
DB_PASSWORD
```

`.env.example` documenta la forma esperada sin contener credenciales reales. Nunca se deben versionar contraseñas, tokens, archivos `.env` ni configuraciones locales sensibles.

## Módulos previstos

1. Inicio / Dashboard
2. Personal
3. Cursos
4. Programación de capacitaciones
5. Participantes
6. Asistencia
7. Resultados
8. Certificados
9. Historial de capacitaciones
10. Vigencias
11. Alertas
12. Reportes
13. Usuarios
14. Roles y permisos
15. Autenticación

## Estado actual

Este bootstrap establece la base Maven, Java 21, el Maven Wrapper, la estructura por capas, la dependencia JDBC de MySQL, JUnit 5 y el punto de entrada mínimo.

Todavía no están implementados la interfaz gráfica, la conexión a MySQL, el modelo físico, tablas, entidades de negocio, repositorios, servicios, controladores, autenticación, CRUD, alertas ni reportes.

El siguiente paso lógico será definir el modelo de dominio y el esquema relacional inicial, antes de implementar la persistencia JDBC y los primeros casos de uso.
