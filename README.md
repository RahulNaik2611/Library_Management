
# 📚 Library Management System – Spring Boot + MySQL + JWT

This is a full-fledged backend system for a library, allowing admins to manage books and users to browse, issue, and return books. It uses **Spring Boot**, **JWT**, and **MySQL**, with secure authentication and role-based access.

---

## 💡 Key Highlights

- ✅ RESTful API using Spring Boot 3.x
- 🔐 Stateless JWT authentication (user & admin roles)
- 🔄 Issue/Return book records maintained
- 🧩 Clean architecture with layered separation (Controller → Service → Repository)
- 🧪 Unit tested endpoints using JUnit and Mockito (optional)
- 🚀 Easy to extend and integrate with frontend apps (React, Angular, etc.)

---

## 🔍 System Design Overview

- **Authentication Flow**:
  - Register or login to receive a JWT token
  - Send the JWT in `Authorization: Bearer <token>` header
  - Access user/admin endpoints based on the role embedded in the token

- **Database Design**:
  - Users, Books, and BookLoans (Issue Records) tables
  - Foreign key relations managed using JPA annotations

---

## 🛠️ Technologies

| Category        | Tool/Library                 |
|----------------|------------------------------|
| Backend        | Spring Boot, Spring MVC      |
| Security       | Spring Security, JWT (jjwt)  |
| Database       | MySQL, JPA (Hibernate)       |
| Language       | Java 17+                     |
| Build Tool     | Maven                        |
| Dev Tools      | Spring DevTools, Lombok      |
| Testing        | JUnit, Mockito               |

---

## 🧱 Folder Structure

```
src/
├── controller/       # REST Controllers
├── dto/              # Data Transfer Objects
├── entity/           # JPA Entities
├── jwt/              # JWT Generation & Validation
├── repository/       # Repository interfaces (JPA)
├── security/         # Security configurations
├── service/          # Business logic
└── application.yml   # Main configuration file
```

---

## 🔧 Environment Setup

1. **Install Java 17+ and Maven**
2. **Install MySQL** and create a database named `libraryManagement`
3. **Configure `application.yml`** with your credentials and secret key

---

## 📦 API Usage Examples

- **Login Request**
```json
POST /auth/login
{
  "username": "rahul",
  "password": "yourPassword"
}
```

- **JWT Auth Header**
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI...
```

- **Add Book (Admin Only)**
```json
POST /books/addbook
{
  "title": "Clean Code",
  "author": "Robert C. Martin"
}
```

---

## 🏗️ Layered Architecture

```
src/
├── main/
│   ├── java/
│   │   ├── controller/       # API endpoints
│   │   ├── dto/             # Data Transfer Objects  
│   │   ├── entity/          # JPA entities
│   │   ├── repository/      # Database interfaces
│   │   ├── security/        # Auth configuration
│   │   ├── service/         # Business logic
│   │   └── util/            # JWT utilities
│   └── resources/
│       └── application.yml  # Configurations
```

```
## 🔄 Flow of Control

1. **Request** → Controller → Service → Repository → Database  
2. **Response** → DTO → Service → Controller → Client



## 🛡️ Security Flow

```mermaid
sequenceDiagram
    Client->>+Controller: Request (No JWT)
    Controller->>Client: 401 Unauthorized
    
    Client->>+AuthController: Login (Credentials)
    AuthController->>JwtService: Generate Token
    JwtService-->>AuthController: JWT
    AuthController-->>Client: Return Token
    
    Client->>+BookController: Request (With JWT)
    BookController->>JwtFilter: Validate Token
    JwtFilter->>BookController: Success
    BookController->>BookService: Process Request
    BookService->>BookRepository: Query DB
    BookRepository-->>BookService: Data
    BookService-->>BookController: DTO
    BookController-->>Client: Response
```

## 📦 Database Schema

```mermaid
erDiagram
    USER ||--o{ BOOK_LOAN : has
    BOOK ||--o{ BOOK_LOAN : referenced
    
    USER {
        long id PK
        string username
        string password
        string role
    }
    
    BOOK {
        long id PK
        string title
        string author
        bool available
    }
    
    BOOK_LOAN {
        long id PK
        date issued
        date returned
        long user_id FK
        long book_id FK
    }
```

## 👨‍💻 Contributor Info

**B Rahul Naik**  
🔧 Java Backend Developer – Spring Boot | SQL | JWT  
📫 Email: banavathrahulnaik26@gmail.com  
🔗 LinkedIn: [rahulnaik-banavath](https://www.linkedin.com/in/rahulnaik-banavath-293699367)

---


## 🧾 License

This project is licensed under the MIT License – feel free to use and customize it.

---

> Feel free to fork, clone, and contribute to this project. Star ⭐ the repository if you find it useful!
