# SIGECAP J&V

Sistema de gestión de cursos de capacitación para el personal del área de Operaciones de J&V Resguardo.

## Objetivo

SIGECAP J&V reemplazará el manejo disperso de capacitaciones mediante legajos, carpetas digitales, archivos Excel, correo, llamadas y WhatsApp. El sistema busca centralizar la información, mejorar la trazabilidad y facilitar el seguimiento de cursos, participantes, asistencia, resultados, certificados y vigencias.

## Alcance

La solución será una aplicación de escritorio Java con base de datos relacional MySQL, operaciones CRUD, acceso mediante JDBC y autenticación basada en usuarios, roles y permisos. El proyecto tiene alcance académico y no contempla un despliegue productivo real.

No será una aplicación web o móvil, ni utilizará microservicios, API REST como arquitectura principal, Spring Boot, React, Next.js ni integraciones con WhatsApp, SMS, SUCAMEC o APN. La interfaz de escritorio se construye con Swing (sin JavaFX ni frameworks visuales externos).

## Stack técnico

- Java 21 LTS, versión oficial del proyecto.
- Maven con Maven Wrapper 3.9.9.
- Swing para la interfaz de escritorio.
- MySQL 8.x / TiDB Cloud (compatible con el protocolo MySQL) como gestor de base de datos.
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
│   │   ├── config/       # Conexión JDBC (DatabaseConnection)
│   │   ├── model/        # Entidades del dominio (Usuario, Rol)
│   │   ├── repository/   # Persistencia JDBC
│   │   ├── security/     # Hashing de contraseñas
│   │   ├── service/      # Reglas de negocio (autenticación, usuarios)
│   │   ├── controller/   # Coordinación entre vistas y servicios
│   │   └── view/         # Interfaz gráfica Swing (LoginView, MainView, InicioPanel)
│   └── resources/        # Recursos de la aplicación
└── test/java/pe/jvresguardo/sigecap/
```

Las capas están preparadas para crecer sin mezclar responsabilidades.

## Clonar y requisitos

```bash
git clone <URL-del-repositorio>
cd jv-resguardo-capacitaciones
```

Requisitos mínimos:

- JDK Eclipse Temurin u otra distribución compatible con Java 21 LTS.
- Git.
- Acceso a Internet en la primera ejecución para descargar Maven Wrapper y dependencias, y en cada ejecución para conectar a la base de datos (TiDB Cloud u otra instancia MySQL 8.x).
- Un archivo `.env` local con las credenciales de base de datos (ver "Configuración de base de datos").

## Compilar, probar y ejecutar

El `.env` no se carga automáticamente: hay que exportar sus variables en la sesión de la terminal antes de ejecutar la aplicación.

Windows PowerShell:

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd clean package
Get-Content .env | ForEach-Object {
    if ($_ -match '^\s*([^#=]+)=(.*)$') {
        [System.Environment]::SetEnvironmentVariable($matches[1].Trim(), $matches[2].Trim())
    }
}
java -jar target/sigecap-0.1.0-SNAPSHOT.jar
```

Linux/macOS:

```bash
./mvnw clean test
./mvnw clean package
set -a && source .env && set +a
java -jar target/sigecap-0.1.0-SNAPSHOT.jar
```

`mvn package` genera `target/sigecap-0.1.0-SNAPSHOT.jar` junto con `target/lib/` (dependencias de runtime, como el driver JDBC de MySQL). El manifiesto del jar ya declara el `Main-Class` y el `Class-Path` hacia `target/lib/`, por lo que **no** debe ejecutarse con `java -cp target/classes ...`: esa forma omite el driver JDBC y cualquier login falla con "No se pudo conectar con la base de datos", aunque las credenciales sean correctas.

## Configuración de base de datos

La base se llama `sigecap_jv` y se accede por JDBC. Las credenciales se leen en tiempo de ejecución desde variables de entorno (`DatabaseConnection`), nunca hardcodeadas:

```text
DB_HOST
DB_PORT
DB_NAME
DB_USER
DB_PASSWORD
```

`.env.example` documenta la forma esperada sin contener credenciales reales. El archivo `.env` real (no versionado, ver `.gitignore`) debe tener esos mismos valores y cargarse en el entorno antes de ejecutar la aplicación, como se muestra arriba. Nunca se deben versionar contraseñas, tokens, archivos `.env` ni configuraciones locales sensibles.

## Flujo de la aplicación

1. Al iniciar, se muestra `LoginView` (usuario y contraseña).
2. `LoginController` valida las credenciales contra `AutenticacionService`, que compara el hash de la contraseña y revisa que el usuario esté activo.
3. Login correcto: se cierra `LoginView` y se abre `MainView` con el usuario autenticado.
4. `MainView` muestra en la barra superior el nombre completo y el rol del usuario, y ofrece un menú lateral (Inicio, Personal, Capacitaciones, Seguimiento, Reportes, Usuarios) que cambia el contenido central mediante `CardLayout`.
5. "Inicio" muestra un panel de bienvenida (`InicioPanel`); los demás módulos aún muestran un aviso de "en desarrollo", ya que sus CRUD no están implementados.
6. "Cerrar sesión" cierra `MainView` y vuelve a mostrar `LoginView`.

Login incorrecto: se muestra un mensaje de error y la aplicación permanece en `LoginView`.

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

Implementado:

- Conexión JDBC a TiDB Cloud (`DatabaseConnection`), configurable por variables de entorno.
- Modelos `Usuario` y `Rol`, repositorios JDBC y servicios (`UsuarioService`, `AutenticacionService`).
- Hashing seguro de contraseñas (`PasswordHasher`).
- Login Swing (`LoginView` + `LoginController`) con autenticación real contra la base de datos.
- Ventana principal (`MainView`) con barra superior, menú lateral y navegación por `CardLayout`, más cierre de sesión.
- Panel de inicio (`InicioPanel`) con bienvenida y rol del usuario.

Pendiente:

- CRUD de Personal, Cursos, Programación, Participantes, Asistencia, Resultados, Certificados, Historial, Vigencias, Alertas, Reportes y Usuarios.
- Matriz de permisos por rol (por ahora todos los usuarios autenticados ven el mismo menú).
- Dashboard con métricas reales.

El siguiente paso lógico es definir el esquema y los casos de uso del primer módulo CRUD (por ejemplo, Personal).
