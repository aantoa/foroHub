# ForoHub API 📚💻

Proyecto desarrollado como parte del **Challenge Back End - Alura Latam**.  
El objetivo es construir una API REST para un foro de discusión, donde estudiantes y profesores pueden crear tópicos, responderlos y mantener la colaboración.

---

## 🚀 Tecnologías utilizadas
- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **Spring Validation**
- **Flyway** (migraciones de base de datos)
- **MySQL 8**
- **Lombok**

---

## 🗄️ Base de datos

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

---

## 📌 Funcionalidades implementadas hasta ahora

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
## 📌 Próximos pasos

- Implementar autenticación JWT.

- Endpoints para Respuestas. (Opcional)

- Gestión de Usuarios y Perfiles. (Opcional)

- Documentación con Swagger. (Opcional)

## 👨‍💻 Autor

Proyecto desarrollado como parte del Challenge Back End de Alura Latam.