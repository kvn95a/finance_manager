# 💰 Finance Manager

A personal finance management web application built with **Java Spring Boot** and **PostgreSQL**. This project allows users to track their income and expenses, manage different accounts, and get insights into their financial habits.

## 🚀 Features

- User registration and authentication (JWT-based)
- Add, edit, and delete transactions
- Track income and expenses over time
- Categorize transactions (e.g., Food, Rent, Salary)
- Dashboard with basic financial insights
- PostgreSQL database integration
- RESTful API backend (frontend in development)

## 🛠️ Tech Stack

- **Backend**: Java, Spring Boot, Spring Security, Spring Data JPA
- **Database**: PostgreSQL
- **Authentication**: JWT (JSON Web Tokens)
- **Build Tool**: Maven
- **Future Frontend**: React (planned, in development)

## 🔧 Environment Setup (Dev)

1) Create your local env file:
```bash
cp .env.example .env
```

2) Edit `.env` and set real values:
- `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
- `JWT_SECRET`, `JWT_ISSUER`, `JWT_EXP_MINUTES`

## 🐳 Docker (Postgres)

Start the database:
```bash
docker compose up -d
```

## ▶️ Run the App (Dev)

Load env vars and start with the dev profile:
```bash
set -a; source .env; set +a
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## 🚀 Production Notes

- Always set `SPRING_PROFILES_ACTIVE=prod`.
- `application-prod.yml` requires all DB/JWT env vars and has no defaults.
- Example:
```bash
export SPRING_PROFILES_ACTIVE=prod
export DB_URL=jdbc:postgresql://<host>:5432/finance_db
export DB_USERNAME=...
export DB_PASSWORD=...
export JWT_SECRET=...
export JWT_ISSUER=finance_manager
export JWT_EXP_MINUTES=60
```
