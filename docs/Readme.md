# 🌟 Lite Thinking - Sistema de Inventario y Gestión de Productos

Proyecto Full Stack desarrollado como prueba técnica para **Lite Thinking**, orientado a la gestión de **empresas, inventarios y productos**, con soporte para **envío de reportes por correo (AWS SES)** y **descarga en PDF**.

---

## 🚀 Arquitectura General

| Capa | Tecnología | Descripción |
|------|-------------|-------------|
| **Frontend** | Next.js + TypeScript | Interfaz web moderna y reactiva. |
| **Backend** | Spring Boot (Java 17) | API RESTful central con validaciones y servicios. |
| **Base de Datos** | MySQL (AWS RDS) | Almacenamiento relacional para entidades de negocio. |
| **Correo & Archivos** | AWS SES & PDFService | Generación de reportes e integración con Amazon Simple Email Service. |
| **Hosting** | AWS Elastic Beanstalk + AWS Amplify | Backend y frontend desplegados en la nube. |

---

## 🧱 Estructura del Proyecto

```
LiteThinking/
│
├── backend/
│   ├── src/main/java/com/litethinking/app/
│   │   ├── domain/          → Entidades JPA (Empresa, Producto, Categoría)
│   │   ├── dto/             → Data Transfer Objects
│   │   ├── mapper/          → Conversión Entity ↔ DTO
│   │   ├── repository/      → Interfaces JPA Repository
│   │   ├── service/         → Lógica de negocio
│   │   ├── web/             → Controladores REST
│   │   └── config/          → Configuración de Spring
│   ├── pom.xml              → Dependencias Maven
│   └── target/              → JAR compilado
│
└── frontend/
    ├── pages/               → Rutas (Next.js)
    ├── components/          → UI Reutilizable
    ├── services/            → Conexión HTTP con backend
    ├── public/              → Recursos estáticos
    ├── package.json         → Dependencias Node.js
    └── amplify.yml          → Script de build para AWS Amplify
```

---

## ⚙️ Backend - Spring Boot

### 🧩 Requisitos
- Java 17 o superior  
- Maven 3.9+  
- MySQL 8.x  
- AWS SDK configurado (para SES si se usa correo)

### 📦 Compilación local
```bash
cd backend
mvn clean package -DskipTests
```

Esto genera el archivo:
```
target/app-0.0.1-SNAPSHOT.jar
```

### ▶️ Ejecución local
```bash
java -jar target/app-0.0.1-SNAPSHOT.jar
```

### 🔧 Configuración en `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/litethinking
spring.datasource.username=root
spring.datasource.password=tu_clave

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

aws.region=us-east-1
aws.ses.sender=no-reply@tudominio.com
```

---

## 💻 Frontend - Next.js

### 📋 Requisitos
- Node.js 18+
- npm 9+

### ▶️ Ejecución local
```bash
cd frontend
npm install
npm run dev
```

Abrir en [http://localhost:3000](http://localhost:3000)

### 🔗 Variables de entorno
Crea un archivo `.env.local`:
```bash
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

---

## 📚 Documentación de la API - Swagger UI

El backend incluye **Swagger UI** para documentar y probar de forma visual los endpoints disponibles.  
Swagger se genera automáticamente con las dependencias de `springdoc-openapi-starter-webmvc-ui`.

### 🔹 Acceso local
Cuando el backend se ejecuta localmente:
http://localhost:8080/swagger-ui/index.html

### 🧩 Ejemplo de rutas disponibles

| Método | Endpoint | Descripción |
|---------|-----------|-------------|
| **GET** | `/api/empresas` | Lista todas las empresas registradas |
| **POST** | `/api/empresas` | Crea una nueva empresa |
| **GET** | `/api/productos` | Lista todos los productos o filtra por empresa |
| **POST** | `/api/productos` | Crea un nuevo producto asociado a una empresa |
| **GET** | `/api/inventario/{nit}` | Genera el inventario por empresa |
| **POST** | `/api/inventario/email` | Envía el inventario al correo usando AWS SES |

---

### ⚙️ Configuración (backend)

Si necesitas verificar o activar Swagger manualmente, asegúrate de tener esta dependencia en el `pom.xml`:

```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.3.0</version>
</dependency>
```

## 📬 Funcionalidades principales

| Módulo | Descripción |
|---------|--------------|
| **Empresas** | CRUD completo con validación de NIT. |
| **Productos** | CRUD + asociación a empresa (por NIT). |
| **Inventario** | Filtrado por empresa, exportación PDF. |
| **AWS SES** | Envío de inventario por correo electrónico. |
| **AWS RDS** | Base de datos persistente y escalable. |
| **AWS Amplify** | Hosting y despliegue automático del frontend. |

---

## 🧠 Consideraciones técnicas

- Se usa **Lombok** para DTOs y entidades.
- Mapeo **Entity ↔ DTO** mediante `AppMapper`.
- Validaciones con `jakarta.validation.constraints`.
- Arquitectura en capas: **Controller → Service → Repository**.
- Frontend usa hooks (`useEffect`, `useState`) y componentes modulares.

---

## 🧾 Autores

**Desarrollado por:**  
👨‍💻 *Augusto Echeverria J.*  
📧 [augustoecheverriaj@gmail.com](mailto:augustoecheverriaj@gmail.com)  
💼 [LinkedIn](https://www.linkedin.com)

---

