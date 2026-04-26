# E-Commerce Microservices

Production-ready Microservices architecture built with Java Spring Boot 4.

## Architecture

Client → API Gateway :8080
├── Auth Service    :8081
├── Product Service :8082
└── Order Service   :8083



## Services

| Service | Port | Description |
|---|---|---|
| API Gateway | 8080 | Single entry point, request routing |
| Auth Service | 8081 | Register, Login, JWT |
| Product Service | 8082 | CRUD, Pagination, Redis Cache |
| Order Service | 8083 | Create order, Stock deduction |

## Tech Stack

- Java 21 + Spring Boot 4
- PostgreSQL + Hibernate
- Redis Cache
- JWT Authentication
- REST inter-service communication

## Quick Start

```bash
# Start dependencies
docker run --name postgres -e POSTGRES_PASSWORD=1234 -e POSTGRES_DB=auth_db -p 5432:5432 -d postgres:15
docker run --name redis -p 6379:6379 -d redis:7

# Run each service in separate terminals
cd auth-service    && mvn spring-boot:run
cd product-service && mvn spring-boot:run
cd order-service   && mvn spring-boot:run
cd api-gateway     && mvn spring-boot:run
```

## API Endpoints (via Gateway)

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/auth/register | Register |
| POST | /api/auth/login | Login |
| GET | /api/products | List products |
| POST | /api/products | Create product |
| POST | /api/orders | Create order |
| GET | /api/orders/my/{username} | My orders |