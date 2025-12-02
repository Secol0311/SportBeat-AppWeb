# Gestión de Ligas Deportivas
>
> ## Descripción
> Este proyecto es una plataforma web para la gestión de ligas deportivas locales, construida con una arquitectura de microservicios.
>
> ## Arquitectura
> - **Gateway (BFF):** Punto de entrada único, maneja la autenticación, enruta peticiones y renderiza las vistas con Thymeleaf.
> - **Microservicios:** Cada servicio gestiona una parte específica del negocio (autenticación, partidos, resultados, etc.).
>
> ## Tecnologías
> - **Backend:** Java 17, Spring Boot 3.2.5, Spring Security, Spring WebFlux.
> - **Frontend:** Thymeleaf, HTML5, CSS3, JavaScript.
> - **Base de Datos:** MySQL 8.0+.
> - **Comunicación:** WebClient para comunicación entre servicios.
> - **Contenerización:** Docker y Docker Compose.
>
> ## Estructura del Proyecto
> ```
> gestion-ligas-deportivas/
> ├── .git/
> ├── .gitignore
> ├── README.md
> ├── docker-compose.yml
> ├── pom.xml
> ├── BD/
> │ ├── auth-service/
> │ │ ├── pom.xml
> │ │ └── src/...
> │ │ └── resources/
> │ │ └── application.yml
> │ │
> │ └── ... (demás archivos del auth-service)
> │
> │
> ├── microservices/
> │ ├── auth-service/
> │ │ ├── pom.xml
> │ │ └── src/...
> │ │ └── resources/
> │ │ └── application.yml
> │ │
> │ └── ... (demás archivos del auth-service)
> │
> │
> │ ├── equipo-jugador-service/
> │ │ ├── pom.xml
> │ │ └── src/...
> │ │ └── resources/
> │ │ └── application.yml
> │ │
> │ └── ... (demás archivos del equipo-jugador-service)
> │
> │
> │ ├── partido-service/
> │ ├── resultado-service/
> │ ├── notificacion-service/
> │ └── ... (demás microservicios)
> │
> │
> └── gateway/
> │ ├── pom.xml
> │ └── src/...
> │ │ └── resources/
> │ │ └── application.yml
> │ │ └── templates/...
> │ │ └── static/...
> │ │
> │ └── ... (demás archivos del gateway)
> │
> │
> └── ... (otros archivos y carpetas)
> ```
>
> ## Cómo Ejecutar el Proyecto
> La forma más sencilla de orquestar todo es con Docker y `docker-compose.yml`.
>
> ### 1. Usando Docker Compose (Recomendado)
>
> Asegúrate de tener Docker y Docker Compose instalados.
>
> En la raíz del proyecto (`gestion-ligas-deportivas/`), ejecuta:
>
> ```bash
> docker-compose up --build
> ```
>
> Este comando:
> 1. Construirá (o reconstruirá) las imágenes de cada microservicio y del gateway.
> 2. Iniciará todos los contenedores definidos en `docker-compose.yml`.
> 3. Los logs de todos los servicios se mostrarán en una sola terminal.
>
> ### 2. Ejecutando Microservicios Individualmente
>
> Si prefieres trabajar en un solo microservicio a la vez, puedes construir y ejecutar cada uno por separado usando Maven.
>
> Ejemplo para el `auth-service`:
>
> ```bash
> cd microservices/auth-service
> mvn spring-boot:run
> ```
>
> ## Licencia
> Este proyecto está licenciado bajo la Licencia MIT. ¡Siéntete libre de usarlo y contribuir!
> ```
>
> ---
>
> ### **Paso 3: El `README.md` para el `auth-service`**
>
> Ahora, dentro de `microservices/auth-service/`, crea un archivo `README.md`.
>
> ```markdown
> # auth-service
>
> ## Descripción
> Este microservicio es el responsable de la autenticación y gestión de usuarios. Gestiona el registro, login y generación de tokens JWT.
>
> ## Tecnologías
> - **Java 17**
> - **Spring Boot 3.2.5**
> - **Spring Security**
> - **Spring Data JPA**
> - **MySQL 8**
> - **JWT (io.jsonwebtoken)**
>
> ## Arquitectura
> - **Controller:** Recibe peticiones HTTP (ej. `POST /api/auth/login`).
> - **Service:** Contiene la lógica de negocio (validar credenciales, generar token JWT).
> - **Repository:** Acceso a la base de datos de usuarios.
> - **Security Config:** Configura Spring Security para proteger los endpoints y usar el `AuthenticationManager`.
> - **JWT Util:** Clase para generar y validar tokens.
>
> ## Estructura de Carpetas
> ```
> microservices/auth-service/
> ├── pom.xml
> ├── src/
> │ └── main/
> │ ├── java/com/sportbeat/auth/
> │ │ ├── controller/
> │ │ │ └── AuthController.java
> │ │ │ └── ...
> │ │
> │ └── resources/
> │ │ └── application.yml
> │ │ └── static/...
> │ │ └── templates/...
> │ │
> │ └── application.yml
> │
> │ └── ... (otros archivos del auth-service)
> │
> ```
>
> ## Endpoints de la API
> - `POST /api/auth/login`: Inicia sesión de usuario y devuelve un token JWT.
> - `POST /api/auth/register`: Registra un nuevo usuario.
> - `POST /api/auth/validate`: Valida credenciales (opcional, si quieres validar un token antes de usarlo).
> - `GET /api/auth/status`: Verificar si el servicio está corriendo.
>
> ## Configuración en `application.yml`
> ```yaml
> server:
> port: 8081
>
> spring:
> application:
> name: auth-service
> datasource:
> url: jdbc:mysql://localhost:3306/auth_db
> username: root
> password: tu_contraseña
> jpa:
> hibernate:
> ddl-auto: update
> show-sql: true
> security:
> user-service:
> name: authenticationManager
> password-encoder: passwordEncoder
> jwt:
> secret: miClaveSecretaParaJWT123456789012345678901234567890
> ```
>
> ## Cómo Ejecutar
> 1. Navega a la carpeta `microservices/auth-service`.
> 2. <strong>Ejecuta:</strong>
> ```bash
> mvn spring-boot:run
