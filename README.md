# 🏦 Banking Payment Processing Platform

## Project Overview
A microservices-based Banking Payment Processing Platform built with Java Spring Boot, containerized using Docker, orchestrated on Azure Kubernetes Service (AKS), and automated using Azure DevOps CI/CD pipelines.

## Architecture
```
Developer (Azure Repos - Git Flow)
        ↓
CI Pipeline (Build → Test → SonarQube → Docker → ACR)
        ↓
CD Pipeline (DEV → QA → PROD with Approval Gates)
        ↓
AKS Cluster (3 environments - dev, qa, prod namespaces)
        ↓
ELK Stack (Centralized Logging - Kibana Dashboards)
```

## Microservices
| Service | Port | Purpose |
|---|---|---|
| api-gateway | 8080 | Routes traffic to all services |
| payment-service | 8081 | Core fund transfer logic |
| transaction-service | 8082 | Transaction history & validation |
| notification-service | 8083 | SMS/Email alerts after payment |
| auth-service | 8084 | JWT token authentication |

## Tech Stack
- **Language:** Java 17 + Spring Boot 3.x
- **Build Tool:** Maven
- **Containerization:** Docker
- **Container Registry:** Azure Container Registry (ACR)
- **Orchestration:** Azure Kubernetes Service (AKS)
- **CI/CD:** Azure DevOps Pipelines
- **Code Quality:** SonarQube
- **Logging:** ELK Stack (Elasticsearch + Logstash + Kibana + Filebeat)
- **Secrets Management:** Azure Key Vault + Variable Groups

## Branch Strategy (Git Flow)
```
main          → Production code only
develop       → Integration branch (all features merge here)
feature/PAY-XXX-description → Developer feature branches
hotfix/PAY-CRIT-XXX         → Emergency production fixes
```

## Pipeline Flow
```
feature branch → PR (2 approvals required) → develop
        ↓
CI Pipeline triggers:
  1. Maven Build + JUnit Tests
  2. SonarQube Analysis (Quality Gate: 80% coverage)
  3. Docker Image Build
  4. Push to ACR (tagged with Build ID)
        ↓
CD Pipeline:
  Stage 1: Deploy to DEV (auto)
  Stage 2: Deploy to QA (auto after DEV passes)
  Stage 3: Deploy to PROD (manual approval by Tech Lead)
```

## Environments
| Environment | Namespace | Replicas | Deployment |
|---|---|---|---|
| DEV | banking-dev | 1 | Auto on develop merge |
| QA | banking-qa | 2 | Auto after DEV |
| PROD | banking-prod | 3 | Manual approval, weekends |

## Deployment Strategy
- **Normal releases:** Rolling Update (zero downtime)
- **Critical payment service changes:** Blue-Green Deployment

## How to Run Locally
```bash
# Clone the repo
git clone https://dev.azure.com/mybank/banking-payment-platform

# Build all services
mvn clean package -f payment-service/pom.xml

# Build Docker image
docker build -t payment-service:latest ./payment-service

# Run locally
docker-compose up
```
