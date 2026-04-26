# 🛒 E-Commerce Microservices (Spring Boot 4)

Production-ready **Microservices Architecture** for E-Commerce system  
Built with Java 21 + Spring Boot 4 — Scalable, Maintainable, and Deploy-ready

---

## 🚀 Overview

This project is an E-Commerce system designed with Microservices architecture.  
Each service is independently deployable with loose coupling and high cohesion.

### 🔥 Features
- 🔐 JWT Authentication (Register / Login)
- 🛍️ Product Management (CRUD + Pagination)
- 📦 Order Management + Stock Deduction
- ⚡ Redis Cache for Performance
- 🌐 API Gateway as Single Entry Point
- 🔗 RESTful Inter-service Communication

---

## 🏗️ Architecture

```
Client
  ↓
API Gateway      :8080
  ├── Auth Service     :8081
  ├── Product Service  :8082
  └── Order Service    :8083
```

---

## 📦 Services

| Service         | Port | Description                          |
|-----------------|------|--------------------------------------|
| API Gateway     | 8080 | Single entry point + Request routing |
| Auth Service    | 8081 | Register / Login / JWT               |
| Product Service | 8082 | Product CRUD + Pagination + Cache    |
| Order Service   | 8083 | Create Order + Stock Management      |

---

## 🛠️ Tech Stack

| Category       | Technology                   |
|----------------|------------------------------|
| Language       | Java 21                      |
| Framework      | Spring Boot 4                |
| Database       | PostgreSQL + Hibernate (JPA) |
| Cache          | Redis                        |
| Auth           | JWT                          |
| Communication  | REST API                     |
| Infrastructure | Docker                       |

---

## ⚙️ Getting Started

### 1. Start Dependencies

```bash
docker run --name postgres \
  -e POSTGRES_PASSWORD=1234 \
  -e POSTGRES_DB=auth_db \
  -p 5432:5432 -d postgres:15

docker run --name redis -p 6379:6379 -d redis:7
```

### 2. Run Services

Open a separate terminal for each service:

```bash
cd auth-service    && mvn spring-boot:run
cd product-service && mvn spring-boot:run
cd order-service   && mvn spring-boot:run
cd api-gateway     && mvn spring-boot:run
```

---

## 🔌 API Endpoints (via Gateway)

### 🔐 Auth
| Method | Endpoint           | Description |
|--------|--------------------|-------------|
| POST   | /api/auth/register | Register    |
| POST   | /api/auth/login    | Login       |

### 🛍️ Product
| Method | Endpoint      | Description       |
|--------|---------------|-------------------|
| GET    | /api/products | List all products |
| POST   | /api/products | Create product    |

### 📦 Order
| Method | Endpoint                  | Description     |
|--------|---------------------------|-----------------|
| POST   | /api/orders               | Create order    |
| GET    | /api/orders/my/{username} | Get user orders |

---

## 🔐 Authentication

After login, attach the JWT token in every request header:

```
Authorization: Bearer <your-token>
```

---

## ⚡ Caching Strategy

- Redis is integrated in **Product Service**
- Reduces repeated database queries
- Significantly improves API response time

---

## 📈 Future Improvements

- [ ] Service Discovery (Eureka / Consul)
- [ ] Message Queue (Kafka / RabbitMQ)
- [ ] Unit & Integration Tests
- [ ] Monitoring Dashboard (Prometheus + Grafana)
- [ ] CI/CD Pipeline (GitHub Actions)
- [ ] Docker Compose for full stack

---

## 👨‍💻 Author

**Watcharapong Ruttum**

---

> This project demonstrates real-world backend skills:  
> Microservices Design · REST API Development · Caching · JWT Auth · System Scalability