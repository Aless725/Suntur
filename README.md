# Suntur ERP

Sistema de gestión académica y administrativa para la **I.E. San José Marello N° 1220** (La Molina, Lima).
Curso: Desarrollo Web Integrado – UTP. Entrega: *Diseño e Implementación* (Frontend, Backend y Persistencia de Bases de Datos).

## Módulos incluidos

| Módulo | Qué permite | Quién |
|---|---|---|
| Acceso | Inicio de sesión, registro de docentes, cierre de sesión | Todos |
| Tablero | Indicadores, asistencia del día, promedio por sección | Todos (montos de cobranza solo ADMIN) |
| Estudiantes | Listar, buscar, registrar, editar y dar de baja | Consulta: todos · Cambios: ADMIN |
| Matrículas | Matricular por año y sección, anular | ADMIN |
| Calificaciones | Notas 0-20 por sección, curso y bimestre | Todos |
| Asistencias | Presente / Tardanza / Falta por sección y fecha | Todos |
| Actividades y pagos | Crear actividades, generar cobros por sección, registrar pagos | ADMIN |
| Reportes | Resumen, rendimiento, boleta de notas imprimible, exportar CSV | Todos (pagos solo ADMIN) |

## Tecnologías

Java 21 · Spring Boot (Spring MVC) · JSP + JSTL · JDBC con patrón DAO · MySQL 8 (H2 opcional para pruebas) · Bootstrap 5 (incluido localmente) + CSS propio · BCrypt para contraseñas.

## Cómo ejecutarlo

### Opción A: con MySQL (configuración principal)
1. Tener MySQL en `localhost:3306` con usuario `root` y contraseña vacía (o cambiar los datos en `src/main/resources/application.properties`, o con las variables de entorno `SUNTUR_DB_URL`, `SUNTUR_DB_USER`, `SUNTUR_DB_PASSWORD`).
2. No hace falta crear nada: al iniciar se crea la base `suntur_db`, las tablas (`src/main/resources/db/schema.sql`) y los datos base.
3. Ejecutar:
   ```
   mvnw spring-boot:run
   ```
   (en Windows `mvnw.cmd spring-boot:run`). Abrir http://localhost:8080

### Opción B: sin instalar MySQL (H2 en memoria)
```
mvnw spring-boot:run -Dspring-boot.run.profiles=h2
```
Los datos se pierden al detener la aplicación.

### Pruebas
```
mvnw test
```
Ejecuta una prueba de integración de la fachada sobre H2 (matrícula, notas, boleta, asistencia y pagos).

## Usuarios de ejemplo

| Rol | Correo | Contraseña |
|---|---|---|
| ADMIN | admin@suntur.edu.pe | Admin123 |
| DOCENTE | docente@suntur.edu.pe | Docente123 |

Cambia estas contraseñas y pon `suntur.seed-demo=false` antes de usar el sistema con datos reales.
Los datos de ejemplo (8 estudiantes, notas, asistencia y pagos) se cargan solo si la base está vacía.

## Estructura

```
src/main/java/com/suntur
├── SunturApplication.java      Arranque
├── config/                     Conexion (JDBC), WebConfig, AuthInterceptor, DatabaseInitializer
├── controller/                 C del MVC: reciben la petición y eligen la vista
├── facade/                     SunturFacade: reglas de negocio y validaciones (patrón Facade)
├── dao/                        Acceso a datos con JDBC (patrón DAO)
├── model/                      Entidades (una por tabla)
└── dto/                        Objetos de transferencia para pantallas y reportes (patrón DTO)
src/main/webapp/WEB-INF/views   V del MVC: páginas JSP
src/main/resources/static       CSS (Bootstrap + suntur.css) y JS
src/main/resources/db           schema.sql
```

## Paleta (colores planos, sin degradados)

Blanco `#FFFFFF` · Guinda `#7A1631` (oscuro `#5C0F24`) · Amarillo `#F4C20D`. Definidos como variables en `static/css/suntur.css`.

## Alcance y mejoras pendientes

Es un desarrollo básico funcional. Queda fuera por ahora: protección CSRF, bloqueo por intentos fallidos de acceso, administración de usuarios desde la interfaz, notas literales de primaria (AD/A/B/C), envío de boletas a padres y despliegue como WAR en un Tomcat externo.
