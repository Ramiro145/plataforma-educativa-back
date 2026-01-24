# Plataforma Educativa – Backend

## Descripción General

Este proyecto es un **backend de una plataforma educativa** desarrollado con **Spring Boot**, enfocado en aplicar **buenas prácticas profesionales** de arquitectura, seguridad y diseño, orientado a un perfil **Junior / Semi-Junior**.

El sistema permite gestionar:

* Usuarios y autenticación
* Roles y permisos
* Cursos
* Profesores
* Estudiantes

Incluye **seguridad basada en JWT**, control de acceso por roles, uso de **DTOs**, **MapStruct** para mapeos, y documentación con **Swagger/OpenAPI**.

---

## Arquitectura del Proyecto

El proyecto sigue una arquitectura **en capas**, separando responsabilidades:

```
Controller  →  Service  →  Repository  →  Database
      ↓           ↓
     DTOs      Mappers
```

### Capas:

* **Controller**: Expone endpoints REST
* **Service**: Lógica de negocio
* **Repository**: Acceso a datos (JPA)
* **Entity**: Modelo de dominio
* **DTO**: Objetos de entrada/salida
* **Mapper**: Conversión Entity ↔ DTO
* **Security**: Autenticación y autorización

---

## Tecnologías Utilizadas

### Backend

* **Java 17**
* **Spring Boot 3.5.x**
* **Spring Data JPA**
* **Spring Security**
* **JWT (JSON Web Token)** – Auth0
* **MapStruct** – Mapeo DTOs
* **Lombok** – Reducción de boilerplate
* **MySQL** – Base de datos
* **Hibernate** – ORM

### Documentación

* **SpringDoc OpenAPI (Swagger UI)**

### Build & Tools

* **Maven**
* **Git / GitHub**

---

## Seguridad

### Autenticación

* Basada en **JWT**
* Login devuelve un **Bearer Token**
* El token se envía en el header:

```
Authorization: Bearer <token>
```

### Autorización

* **Roles**: `ADMIN`, `USER`, etc.
* Roles almacenados como `ROLE_ADMIN`, `ROLE_USER`
* Control con anotaciones:

```java
@PreAuthorize("hasRole('ADMIN')")
```

### Seguridad por Endpoints

* Login y registro: públicos
* Resto de endpoints: protegidos
* Swagger configurado para usar JWT

---

## Roles y Permisos

Relación **Muchos a Muchos**:

* Un **Role** puede tener múltiples **Permissions**
* Un **Permission** puede pertenecer a múltiples **Roles**

Ejemplos de permisos:

* READ
* CREATE
* UPDATE
* DELETE
* EXECUTE

---

## MapStruct & DTOs

### Uso de DTOs

* **RequestDTO**: entrada de datos
* **ResponseDTO**: salida completa
* **SummaryDTO**: vistas resumidas

### MapStruct

* Convierte automáticamente Entity ↔ DTO
* Configuración centralizada:

```java
@MapperConfig(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
```

Se evita la **dependencia circular** entre mappers:

* `CourseMapper` no depende de `ProfessorMapper` y viceversa
* Se usan `SummaryDTOs` para romper ciclos

---

## Modelo de Dominio (UML)

El proyecto incluye un **UML de dominio**, representando:

* Usuarios
* Roles
* Permisos
* Cursos
* Profesores
* Estudiantes

Las relaciones reflejan la lógica real del negocio, no la implementación técnica.

---

## Swagger / OpenAPI

Acceso:

```
http://localhost:8080/swagger-ui.html
```

Configurado con:

* Bearer Token
* SecurityRequirement global
* Login sin autenticación

---

## Objetivo del Proyecto

Este proyecto está diseñado para:

* Demostrar **bases sólidas en backend Java**
* Aplicar **buenas prácticas reales**
* Ser una **base escalable**
* Servir como **proyecto de portafolio** para vacantes Junior

---

## Posibles Mejoras Futuras
* Refresh Tokens
* Tests unitarios y de integración
* Paginación y filtros
* Cache con Redis
* Dockerización
---
Proyecto enfocado en aprender, crecer y demostrar competencias reales de desarrollo backend profesional.
