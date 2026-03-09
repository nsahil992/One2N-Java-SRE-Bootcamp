# Student API – Spring Boot + PostgreSQL + Docker + Kubernetes + Helm

A production-style **Student CRUD REST API** built using **Java, Spring Boot, Spring Data JPA, and PostgreSQL**.

The application allows users to:

- Add new students
- Fetch all students
- Retrieve student details using ID
- Update student information
- Delete student records

This project demonstrates:

- REST API design best practices
- Twelve-Factor App configuration
- Docker containerization
- Local development using Docker Compose
- CI pipeline automation with GitHub Actions
- Kubernetes deployment
- Helm chart based application packaging

---

# Features

## Core Features

- Student CRUD REST APIs
- PostgreSQL database persistence
- API versioning (`/api/v1`)
- Healthcheck endpoint
- Clean layered architecture
- Unit tests for API endpoints

---

## DevOps Features

- Multi-stage Docker build
- Docker Compose development environment
- Makefile automation
- GitHub Actions CI pipeline
- Kubernetes deployment
- Helm chart packaging

---

# Tech Stack

| Category | Technology |
|--------|--------|
| Language | Java 21 |
| Framework | Spring Boot |
| Database | PostgreSQL |
| ORM | Spring Data JPA |
| Build Tool | Maven |
| Containerization | Docker |
| Local Development | Docker Compose |
| Automation | GNU Make |
| CI Pipeline | GitHub Actions |
| Container Registry | DockerHub |
| Orchestration | Kubernetes |
| Package Manager | Helm |

---

# Architecture

```
Client (Postman)
        │
        ▼
Spring Boot REST API
        │
        ▼
PostgreSQL Database
```

Deployment Stack

```
Spring Boot
      │
Docker
      │
Docker Compose
      │
GitHub Actions CI/CD
      │
Kubernetes
      │
Helm
```

---

# API Endpoints

Base URL

```
/api/v1/students
```

---

## 1 Create Student

**POST** `/api/v1/students`

Request

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "age": 20
}
```

Response

```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "age": 20
}
```

---

## 2 Get All Students

**GET** `/api/v1/students`

Response

```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "age": 20
  }
]
```

---

## 3 Get Student By ID

**GET** `/api/v1/students/{id}`

Example

```
GET /api/v1/students/1
```

---

## 4 Update Student

**PUT** `/api/v1/students/{id}`

Request

```json
{
  "name": "John Updated",
  "email": "john@example.com",
  "age": 21
}
```

---

## 5 Delete Student

**DELETE** `/api/v1/students/{id}`

---

## Healthcheck Endpoint

**GET**

```
/api/v1/healthcheck
```

Returns

```
UP
```

Used for:

- Kubernetes readiness probes
- Kubernetes liveness probes
- Service monitoring

---

# Environment Configuration

Configuration follows **Twelve-Factor App principles**.

All sensitive configuration is injected via **environment variables**.

Example variables

.env
```
DB_HOST=localhost/host.docker.internal/database
DB_PORT=5432
DB_NAME=studentdb
DB_USERNAME=postgres
DB_PASSWORD=your_password
DB_URL=jdbc:postgresql://localhost/host.docker.internal/database:5432/studentdb
PORT=8080
VERSION=your_version

Note: choose the DB_HOST and DB_URL as per the development stage
```

These variables can be provided through:

- `.env` files
- Docker environment variables
- Kubernetes ConfigMaps
- Kubernetes Secrets

---

# Project Structure

```
student-api
│
├── src/main/java/com/example/student_api
│   ├── controller
│   │   ├── StudentController.java
│   │   └── HealthController.java
│   │
│   ├── entity
│   │   └── Students.java
│   │
│   ├── repository
│   │   └── StudentRepository.java
│   │
│   ├── service
│   │   └── StudentService.java
│   │
│   └── StudentApiApplication.java
│
├── src/main/resources
│   ├── application.yml
│   └── db/migration/schema.sql
│
├── src/test/java
│   └── StudentApiApplicationTests.java
│
├── Dockerfile
├── docker-compose.yml
├── Makefile
├── pom.xml
├── helm
│   └── student-api
│       ├── Chart.yaml
│       ├── values.yaml
│       └── templates
│            ├── application.yaml
│            ├── database.yaml
│            ├── postgres-secret.yaml
│            └── postgres-pvc.yaml
└── .github/workflows
    └── cicd.yml
```

---

# Running Locally

## Clone Repository

```bash
git clone https://github.com/nsahil992/student-api.git
```

---

## Create Database

```sql
CREATE DATABASE studentdb;
```

---

## Run Application

```bash
./mvnw spring-boot:run
```

Application runs at

```
http://localhost:8080
```

---

# Docker

The application uses a **multi-stage Dockerfile** to produce a smaller production image.

Build Docker image

```bash
docker build -t your_dockerhub_username/student-api:2.0.0 .
```

Run container

```bash
docker run --env-file .env -p 8080:8080 your_dockerhub_username/student-api:2.0.0
```

---

# Docker Compose Local Development

Docker Compose runs the **API and PostgreSQL together**.

Start services

```bash
docker compose up --build
```

Application will be available at

```
http://localhost:8080
```

---

# Makefile Automation

Makefile simplifies the development workflow.

Example commands

```bash
make build
make test
make lint
make docker-build
make docker-push
```

These commands are also used inside the CI pipeline.

---

# CI Pipeline (GitHub Actions)

The repository includes a **CI pipeline using GitHub Actions** with a **self-hosted runner**.

Pipeline stages:

1. Checkout repository  
2. Build application  
3. Run tests  
4. Perform lint checks  
5. Login to DockerHub  
6. Build Docker image  
7. Push Docker image  

The pipeline runs when:

- Code changes are pushed to selected branches
- Pull requests are created
- Manually triggered using `workflow_dispatch`

---

# Kubernetes Deployment

The application can be deployed to **Kubernetes (Minikube)**.

The deployment includes:

- Namespace for isolation
- Spring Boot API Deployment
- PostgreSQL Deployment
- Kubernetes Services
- ConfigMaps for environment variables
- Secrets for database credentials
- Persistent Volume for database storage
- Init container to ensure DB readiness

---

## Deploy to Kubernetes

Apply manifests

```bash
kubectl apply -f k8s/
```

Check pods

```bash
kubectl get pods -n student-api
```

Access API

```bash
kubectl port-forward svc/student-api-service 8080:80 -n student-api
```

Application will be available at

```
http://localhost:8080
```

---

# Helm Deployment

The application is packaged using **Helm charts**.

Helm chart location

```
helm/student-api
```

---

## Install Helm Chart

```bash
helm install student-api ./helm/student-api -n student-api --create-namespace
```

---

## Upgrade Deployment

```bash
helm upgrade student-api ./helm/student-api -n student-api
```

---

## Uninstall

```bash
helm uninstall student-api -n student-api
```

---

# Future Improvements

Planned improvements include:

- ArgoCD GitOps deployment
- Prometheus monitoring
- Grafana dashboards
- Observability improvements

---

# Author

**Sahil Naik**

GitHub

```
https://github.com/nsahil992
```
