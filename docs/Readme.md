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

## 🧩 Despliegue en AWS

### 🔹 **1. Base de Datos (RDS)**
1. Crear instancia MySQL en Amazon RDS.  
2. Configurar usuario y contraseña (`admin` por defecto).  
3. Guardar endpoint del RDS (por ejemplo: `lt-mysql.cacdefgh123.us-east-1.rds.amazonaws.com`).

### 🔹 **2. Backend (Elastic Beanstalk)**
1. Ejecutar:
   ```bash
   mvn clean package -DskipTests
   ```
2. Subir el JAR generado (`target/app-0.0.1-SNAPSHOT.jar`) a Elastic Beanstalk.  
3. En **Variables de entorno** del entorno EB:
   ```bash
   SPRING_DATASOURCE_URL=jdbc:mysql://<RDS-ENDPOINT>:3306/litethinking
   SPRING_DATASOURCE_USERNAME=admin
   SPRING_DATASOURCE_PASSWORD=TuPassword
   AWS_REGION=us-east-1
   AWS_SES_SENDER=no-reply@tudominio.com
   ```

4. Asegurar que el grupo de seguridad del Beanstalk tenga acceso al puerto `3306` del RDS.

---

### 🔹 **3. Frontend (AWS Amplify)**
1. Subir el proyecto `frontend/` a GitHub.  
2. Ir a [AWS Amplify Console](https://console.aws.amazon.com/amplify/) → “Host your web app”.  
3. Conectar el repositorio GitHub.  
4. En las variables de entorno:
   ```bash
   NEXT_PUBLIC_API_URL=https://<tu-backend-elastic-beanstalk>.elasticbeanstalk.com/api
   ```
5. En el archivo `amplify.yml`:
   ```yaml
   version: 1
   frontend:
     phases:
       preBuild:
         commands:
           - npm ci
       build:
         commands:
           - npm run build
     artifacts:
       baseDirectory: .next
       files:
         - '**/*'
     cache:
       paths:
         - node_modules/**/*
   ```

6. Deploy → Amplify genera dominio:  
   `https://main.<hash>.cloudfront.net`

---

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

## 🏁 Licencia
Proyecto de uso académico y demostrativo © 2025 - Lite Thinking  
Distribuido bajo licencia **MIT**.
