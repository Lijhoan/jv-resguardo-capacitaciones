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

## Sistema visual de SIGECAP J&V

SIGECAP es una aplicación desktop académica en Java Swing. Los mockups
son referencia funcional y de estructura, no una obligación
pixel-perfect, pero el sistema debe tener una identidad visual única:
minimalista, limpia, profesional, intuitiva, moderna dentro de lo
posible en Swing, consistente entre módulos y sin elementos
innecesarios. No sobrecargar el diseño.

> **Regla de diseño principal:** antes de implementar una nueva
> pantalla, verificar que conserve el sistema visual de SIGECAP J&V.
> La funcionalidad es prioritaria, pero una pantalla funcional que
> rompa completamente la coherencia visual del sistema no se
> considera terminada.

### 1. Estructura general

- Una sola ventana principal. Flujo: `LoginView` → `MainView` →
  vistas integradas.
- No abrir un `JFrame` distinto por cada módulo.
- `MainView` es el shell permanente de la aplicación: sidebar lateral,
  barra superior y área central de contenido.
- Las vistas internas cambian dentro del área central, preferentemente
  con `CardLayout` u otra solución Swing simple.

### 2. Sidebar / menú lateral

- Menú principal vertical, ubicado a la izquierda.
- Opciones: Inicio, Personal, Capacitaciones, Seguimiento, Reportes,
  Usuarios.
- Debe poder expandirse (icono + nombre de opción) y contraerse
  (ancho reducido, solo iconos, navegación funcional), mediante un
  botón claramente identificable.
- Sin animaciones complejas.
- La opción activa debe distinguirse visualmente.
- No agregar librerías externas solo para el sidebar.

### 3. Iconografía

- Cada opción del menú debe identificarse con un icono, con una
  estrategia simple compatible con Swing. Prioridad:
  1. recursos gráficos locales propios del proyecto, si existen;
  2. iconos simples sin nuevas dependencias;
  3. símbolos Unicode compatibles como solución provisional.
- No usar emojis decorativos inconsistentes.
- Los iconos cumplen función de navegación, no de decoración.

### 4. Paleta y estilo

- Paleta reducida y consistente: sidebar en tono oscuro sobrio,
  contenido principal en fondo claro, color de acento azul, textos
  principales oscuros, textos secundarios en gris, estados de
  error/éxito solo cuando corresponda.
- Evitar: degradados innecesarios, bordes gruesos, botones
  excesivamente grandes, sombras artificiales, colores saturados,
  estética tipo dashboard web recargado.

### 5. Tipografía y jerarquía

- Jerarquía clara: título de página, subtítulo/contexto, secciones,
  etiquetas, contenido.
- Los títulos no deben competir visualmente con los controles.
- Tamaños coherentes entre pantallas; no cambiar tipografía o tamaño
  arbitrariamente entre módulos.

### 6. Formularios

- Campos alineados, espaciado uniforme, campos relacionados agrupados,
  etiquetas claras, sin campos demasiado anchos sin necesidad.
- Botón principal y secundario diferenciados, ej.: `[Cancelar] [Guardar]`
  (Guardar = acción principal, Cancelar = acción secundaria).
- No colocar controles dispersos por toda la pantalla.

### 7. Tablas

- Usarlas solo para información tabular real.
- Encabezados entendibles, buen uso del espacio, lectura cómoda, sin
  columnas innecesarias, estado mostrado con claridad.
- Las acciones principales van fuera de la tabla o se mantienen
  simples.

### 8. Pestañas

- Si un módulo tiene varias funciones relacionadas, usar vistas
  integradas o `JTabbedPane` (ej. Personal: Listado / Registrar-Editar
  / Historial).
- No abrir ventanas adicionales para estas funciones.

### 9. Barra superior

- Debe mostrar, como mínimo: nombre del sistema o módulo actual,
  usuario autenticado, rol y opción de cerrar sesión.
- Evitar duplicar información ya visible en otro lugar.

### 10. Consistencia

Antes de crear una vista nueva: revisar `MainView`, revisar al menos
una vista existente, reutilizar el mismo lenguaje visual y mantener
márgenes, tamaños y jerarquía similares. No diseñar cada módulo desde
cero: Personal, Capacitaciones, Seguimiento, Reportes y Usuarios deben
sentirse parte del mismo programa.

### 11. Mockups

Los mockups del informe son referencia funcional, de distribución y de
contenido, no una obligación pixel-perfect. Se puede mejorar
alineación, espaciado, navegación, jerarquía, legibilidad y
consistencia visual, pero no eliminar funcionalidades documentadas
solo para simplificar el diseño.

### 12. Simplicidad técnica

La interfaz debe mantenerse implementable con Swing estándar.

No agregar: frameworks visuales pesados, JavaFX, librerías de temas
solo por estética, arquitecturas complejas de componentes, sistemas
CSS artificiales, animaciones sofisticadas.

Preferir, cuando sean suficientes: `JPanel`, `JFrame`, `CardLayout`,
`BorderLayout`, `GridBagLayout`, `JTabbedPane`, `JTable`, `JButton`,
`JLabel`.

### 13. Experiencia de usuario

Toda pantalla debe permitir entender rápidamente: dónde estoy, qué
información estoy viendo, qué acción puedo realizar y cómo volver o
cambiar de módulo. Evitar interfaces donde el usuario tenga que
adivinar qué hacer. Las acciones destructivas o cambios de estado
importantes deben pedir confirmación cuando corresponda.

### 14. Responsive desktop

No diseñar para web ni móvil, pero las vistas deben comportarse
razonablemente al redimensionar la ventana. Preferir LayoutManagers y
evitar posiciones absolutas: no usar `setBounds()` para construir
pantallas completas.
