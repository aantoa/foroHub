# ForoHub API 📚💻
[![Licencia MIT](https://img.shields.io/badge/Licencia-MIT-green.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-17%2B-blue.svg)](https://www.oracle.com/java/technologies/downloads/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.9-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8.1-blue?logo=apachemaven)](https://maven.apache.org/)
[![Spring Security](https://img.shields.io/badge/Spring_Security-Enabled-3CB371?logo=springsecurity)](https://spring.io/projects/spring-security)
[![JWT](https://img.shields.io/badge/JWT-Auth-black?logo=jsonwebtokens)](https://jwt.io/)
[![Auth0 Java JWT](https://img.shields.io/badge/Auth0-java--jwt-EB5424?logo=auth0)](https://github.com/auth0/java-jwt)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql)](https://www.mysql.com/)
[![Flyway](https://img.shields.io/badge/Flyway-Migrations-CC0200?logo=flyway)](https://flywaydb.org/)
[![Lombok](https://img.shields.io/badge/Lombok-Used-BC2C1A)](https://projectlombok.org/)

Proyecto desarrollado como parte del **Challenge Back End - Alura Latam**.  
El objetivo es construir una API REST para un foro de discusión, donde estudiantes y profesores pueden crear tópicos, responderlos y mantener la colaboración.
Ahora la API cuenta con autenticación y autorización con JWT 🔐.

---

## 🚀 Tecnologías utilizadas
- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **Spring Validation**
- **Flyway** (migraciones de base de datos)
- **MySQL 8**
- **Lombok**
- **Auth0 Java JWT**

---
## ⚙️ Instalación y configuración
### 1️⃣ Clonar el repositorio
```
git clone https://github.com/tu-usuario/foro-hub.git
cd foro-hub
```
### 2️⃣ Configurar base de datos
```
CREATE DATABASE foro_hub;
```
### 3️⃣ Variables de entorno
En tu sistema o en IntelliJ IDEA / Spring Boot run configuration, define:
```
DB_HOST=localhost
DB_NAME=foro_hub
DB_USER=usuario
DB_PASSWORD=password
JWT_SECRET=clave_secreta
```
Estas variables son usadas en `applications.properties`

### 4️⃣ Ejecutar migraciones
Migraciones gestionadas con **Flyway** (`db/migration`):
- `V1__create_table_usuarios.sql`
- `V2__create_table_perfiles.sql`
- `V3__create_table_usuarios_perfiles.sql`
- `V4__create_table_cursos.sql`
- `V5__create_table_topicos.sql`
- `V6__create_table_respuestas.sql`

Además, se incluyen **semillas iniciales** (`INSERT`) para:
- Usuarios (perfiles `ROLE_ADMIN`, `ROLE_MODERATOR`, `ROLE_PROFESSOR`, `ROLE_USER` )
- Cursos
- Usuario administrador (`admin@forohub.com / admin123`)
- Usuario estudiante (`user_student@forohub.com / user123`)

### 5️⃣ Correr la aplicación

```
./mvnw spring-boot:run
```
o desde IntelliJ ejecutando la clase principal `ForoHubApplication`.

---

## 📌 Endpoints implementados
Por defecto, la app levanta en:
```
http://localhost:8080
```
### 🔐 Autenticación con JWT
- **Login**:
  - `POST /auth/login`
  - Ingresa credenciales ingresadas de usuario: email y password y devuelve un token
  - Una vez obtenido el token, este se debe enviar en el header de cada request

### 🔹 Tópicos
- **Crear** un tópico:
    - `POST /topicos`
    - Validaciones:
        - No permitir tópicos duplicados con mismo título y mensaje.
        - Usuario y curso deben existir en BD.
    - Errores controlados:
        - `400` → Datos inválidos.
        - `404` → Usuario/curso no existe.
        - `422` → Duplicado.
 
- **Listar tópicos**
    - `GET /topicos`
    - Devuelve un listado resumido de todos los tópicos creados.
    - Filtros opcionales
      - `anio` (Integer) – Año de creación del tópico (ej. `2025`)
      - `cursoId` (Long) – Id del curso

    - Se pueden combinarlos libremente:
      - Todos:  
        `GET /topicos`
      - Por año:  
        `GET /topicos?anio=2025`
      - Por curso:  
        `GET /topicos?cursoId=1`
      - Por año y curso:  
        `GET /topicos?anio=2025&cursoId=1`

- **Listar detalle de un tópico**
    - `GET /topicos/{id}`
    - Devuelve los datos de un tópico específico.
    - `404` si no existe.

- **Actualizar** un tópico:
    - `PUT /topicos/{id}`
    - Se pueden modificar: `titulo`, `mensaje`, `status`, `cursoId`.
    - Valida duplicados y existencia de curso.
    - `404` o `422` según corresponda.

- **Eliminar** un tópico:
    - `DELETE /topicos/{id}`
    - Elimina solo si existe, en caso contrario devuelve `404`.

---

## 🛠️ Manejo de errores

Centralizado en `GlobalExceptionHandler`:
- **404**: recurso no encontrado (`EntityNotFoundException`)
- **400**: validaciones de campos (`MethodArgumentNotValidException`)
- **422**: recurso duplicado (`DuplicateResourceException`)

---
## 📬 Ejemplos de requests (Insomnia/Postman)
### Login 
- Request
```json
{
  "email": "user_student@forohub.com",
  "password": "user123"
}
```
- Response
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "type": "Bearer"
}
```

### Crear tópico
```json
POST /topicos
{
  "titulo": "Problema con JPA",
  "mensaje": "No me reconoce la anotación @Entity.",
  "autorId": 1,
  "cursoId": 1
}
```
### Actualizar tópico
```json
PUT /topicos/1
{
"titulo": "Problema con Maven",
"mensaje": "Error al compilar.",
"status": "CERRADO",
"cursoId": 1
}
```
### Eliminar tópico
```json
DELETE /topicos/1
```
## 📸 Capturas

- ### Login
  - Login exitoso
  
  ![img.png](img/img.png)

  - Error en el login
  
  ![img_1.png](img/img_1.png)

- ### Crear tópico exitoso ✅

    ![img_2.png](img/img_2.png)

- ### Actualización de tópico ✏️

    ![img_3.png](img/img_3.png)

- ### Eliminación de tópico 🗑️
    
    ![img_4.png](img/img_4.png)

- ### Listar tópico por ID 🔎
    
    ![img_5.png](img/img_5.png)

  - ### Listar todos los tópicos 📑

    - Listar todos sin filtros

    ![img_14.png](img/img_14.png)
  
  - Listar topicos con filtros opcionales: 
    
    ![img_15.png](img/img_15.png) 

- ### Validación 400 (Bad Request) 🚫
  
    ![img_16.png](img/img_16.png)

  - ### Validación 404 (Usuario, curso, topico no existe) 🚫

      - Crear Topico
      
        ![img_9.png](img/img_9.png)
    
        ![img_7.png](img/img_7.png)

      - Listar Topico
        
        ![img_6.png](img/img_6.png)
    
      - Actualizar topico
    
        ![img_10.png](img/img_10.png)
    
      - Eliminar topico
        
        ![img_11.png](img/img_11.png)

- ### Validación 422 (Tópico duplicado) 🚫

  - Crear Topico
        
    ![img_12.png](img/img_12.png)
  
  - Actualizar topico
  
    ![img_13.png](img/img_13.png)

---

## 👨‍💻 Autor

Proyecto desarrollado como parte del Challenge Back End de Alura Latam.