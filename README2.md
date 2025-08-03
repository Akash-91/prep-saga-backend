# 🧠 PrepSaga Backend

A Spring Boot microservice for managing authentication, topic-based learning content, and secure API access — part of the PrepSaga full-stack system for interview preparation.

---

## ⚙️ Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- Spring Data JPA + PostgreSQL
- OAuth2 (Google Login)
- Docker
- Azure App Services
- GitHub Actions CI/CD
- Azure Application Insights

---

## 🚀 Features

- 🔐 Secure user authentication with JWT
- 🔗 Google OAuth2 login integration
- 📚 CRUD APIs for topic-based content (System Design, DSA, etc.)
- 📦 Dockerized microservice, deployable via GitHub Actions
- 🔍 Integrated with Azure App Insights for monitoring
- 🔐 Environment variables and secrets securely managed (Azure App Settings, Key Vault)

---

## 🏗️ Project Structure
src/
└── main/
├── java/com/prepsaga/
│ ├── config # Security config, JWT filter
│ ├── controller # REST controllers (Auth, Topic)
│ ├── entity # JPA entities
│ ├── repository # Spring Data Repositories
│ ├── service # Business logic
│ └── ...
└── resources/
├── application.yml
└── ...

yaml
Copy
Edit

---

## 📦 API Overview

| Method | Endpoint              | Description                  |
|--------|------------------------|------------------------------|
| POST   | `/api/auth/register`   | User registration            |
| POST   | `/api/auth/login`      | User login with JWT          |
| GET    | `/api/topics`          | List all topics              |
| GET    | `/api/topics/{id}`     | Get topic details            |
| POST   | `/api/topics`          | Create new topic             |

---

## 🐳 Docker

```bash
# Build Docker image
docker build -t prep-backend .

# Run locally
docker run -p 8080:8080 --env-file .env prep-backend
