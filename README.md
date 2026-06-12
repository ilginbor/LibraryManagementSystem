# Library Management System

A full-stack, comprehensive library management system designed for administrators, librarians, and students. This system handles book cataloging, borrowing operations, reservations, user management, and detailed reporting.

## 🚀 Features

- **Authentication & Authorization**: Secure role-based access control (Admin, Librarian, Student) using JWT.
- **Book Management**: Comprehensive CRUD operations for managing the library catalog.
- **Borrowing System**: Track borrowed books, due dates, and manage returning processes seamlessly.
- **Reservation System**: Automated reservation management allowing users to queue for unavailable books.
- **Reporting & Analytics**: Insights into the most borrowed books, active users, and system statistics.
- **Dataset Integration**: Support for importing the Kaggle Books Dataset directly into the system.
- **Containerized Deployment**: Fully dockerized backend, database, and frontend for ease of setup.

## 🛠 Technology Stack

### Backend
- **Java 26**
- **Spring Boot 4.0.1**
- **Spring Data JPA**
- **Spring Security (JWT)**
- **PostgreSQL**
- **Flyway** (Database Migrations)
- **Lombok**
- **Jakarta Validation**

### Frontend
- **React**
- **Vite**

### Infrastructure
- **Docker & Docker Compose**

## 📋 Prerequisites

Before you begin, ensure you have the following installed:
- [Java 26](https://jdk.java.net/26/)
- [Node.js](https://nodejs.org/) (for the React Frontend)
- [Docker & Docker Compose](https://www.docker.com/)
- [PostgreSQL](https://www.postgresql.org/) (if running locally without Docker)
- [Maven](https://maven.apache.org/)

## ⚙️ Getting Started

### 1. Clone the repository
```bash
git clone <repository-url>
cd LibraryManagementSystem
```

### 2. Database Setup
The application uses PostgreSQL. You can either use a local installation or spin one up using Docker. The database schema and initial state are managed automatically via Flyway migrations upon application startup.

### 3. Running the Backend
Navigate to the root directory or backend directory (if structured separately) and start the Spring Boot application:
```bash
./mvnw spring-boot:run
```

### 4. Running the Frontend
Navigate to the frontend directory:
```bash
cd frontend
npm install
npm run dev
```

### 5. Running with Docker Compose (Future Phase)
Once fully dockerized, you can launch the entire stack (Database, Backend, and Frontend) with:
```bash
docker-compose up -d
```

## 🏗 Architecture
The backend follows a **Feature-Based Architecture**, ensuring that each domain feature (e.g., Book, User, Borrow) encapsulates its own `Controller`, `Service`, `Repository`, `Entity`, and `DTOs`. Shared logic is maintained in `common`, `config`, `security`, and `exception` packages.

## 📄 API Documentation
*(To be generated: Swagger/OpenAPI endpoints will be available at `/swagger-ui.html` once configured.)*

## 📝 License
This project is licensed under the MIT License.
