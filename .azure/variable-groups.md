# ============================================================
# .azure/variable-groups.md
# Azure DevOps Variable Groups Setup Guide
# ============================================================

## Overview
Variable groups store environment-specific values used across pipelines.
Secrets are stored in Azure Key Vault and linked to variable groups.

---

## Variable Group 1: banking-dev-variables
**Used in:** DEV environment pipelines

| Variable Name     | Value                          | Secret? |
|-------------------|-------------------------------|---------|
| ACR_NAME          | mybank.azurecr.io             | No      |
| IMAGE_NAME        | payment-service               | No      |
| NAMESPACE         | banking-dev                   | No      |
| AKS_CLUSTER_NAME  | aks-banking-dev               | No      |
| RESOURCE_GROUP    | rg-banking-dev                | No      |
| DB_PASSWORD       | (from Key Vault)              | ✅ Yes  |
| API_SECRET_KEY    | (from Key Vault)              | ✅ Yes  |

---

## Variable Group 2: banking-qa-variables
**Used in:** QA environment pipelines

| Variable Name     | Value                          | Secret? |
|-------------------|-------------------------------|---------|
| ACR_NAME          | mybank.azurecr.io             | No      |
| IMAGE_NAME        | payment-service               | No      |
| NAMESPACE         | banking-qa                    | No      |
| AKS_CLUSTER_NAME  | aks-banking-qa                | No      |
| RESOURCE_GROUP    | rg-banking-qa                 | No      |
| DB_PASSWORD       | (from Key Vault)              | ✅ Yes  |
| API_SECRET_KEY    | (from Key Vault)              | ✅ Yes  |

---

## Variable Group 3: banking-prod-variables
**Used in:** PROD environment pipelines

| Variable Name     | Value                          | Secret? |
|-------------------|-------------------------------|---------|
| ACR_NAME          | mybank.azurecr.io             | No      |
| IMAGE_NAME        | payment-service               | No      |
| NAMESPACE         | banking-prod                  | No      |
| AKS_CLUSTER_NAME  | aks-banking-prod              | No      |
| RESOURCE_GROUP    | rg-banking-prod               | No      |
| DB_PASSWORD       | (from Key Vault)              | ✅ Yes  |
| API_SECRET_KEY    | (from Key Vault)              | ✅ Yes  |

---

## Azure Key Vault Integration
All secret variables are fetched from Azure Key Vault:

```
Azure Key Vault: kv-banking-prod
  ├── db-password          → DB_PASSWORD
  ├── api-secret-key       → API_SECRET_KEY
  ├── acr-admin-password   → ACR_PASSWORD
  └── jwt-secret           → JWT_SECRET
```

## How to Create in Azure DevOps:
1. Go to **Pipelines → Library → + Variable group**
2. Name it `banking-dev-variables`
3. Toggle **Link secrets from Azure Key Vault**
4. Select your Key Vault
5. Add variables
6. Save

---

## Service Connections Setup

| Connection Name        | Type               | Used For                    |
|------------------------|--------------------|-----------------------------|
| acr-service-connection | Docker Registry    | Push images to ACR          |
| aks-service-connection | Kubernetes         | Deploy to AKS cluster       |
| sonar-service-connection| SonarQube         | Code quality analysis       |
| azure-rm-connection    | Azure Resource Mgr | Access Azure resources      |

---

## Branch Policies (Azure Repos)
Configured on `develop` and `main` branches:

- ✅ Minimum 2 reviewers required
- ✅ Build validation must pass (CI pipeline)
- ✅ SonarQube Quality Gate must pass
- ✅ Work item linking required (PAY-XXX ticket)
- ✅ Comment resolution required
- ✅ No direct push to main/develop
