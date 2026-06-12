# Project Status

## Project Name

Library Management System

## Current Phase

Phase 2 & 3 — Authentication & User/Role Management

## Overall Progress

### Setup

* [x] Spring Boot project created
* [x] Dependencies selected (JPA, Security, PostgreSQL, Flyway, Lombok, Validation, Docker)
* [x] Git repository initialized properly
* [x] README created
* [x] .ai context system created

---

### Phase Breakdown

## Phase 0 — Project Initialization

Status: ✅ COMPLETED
Commit: `docs: add project roadmap, ai context files and readme`

Tasks:

* [x] Create Spring Boot base project
* [x] Initialize Git with conventional commit workflow
* [x] Create .ai context files
* [x] Define package structure
* [x] Create initial README

---

## Phase 1 — Database Design & Migration

Status: ✅ COMPLETED
Commit: `feat(db): add flyway v1 schema migration and jpa entities`

Tasks:

* [x] Create Flyway migration V1__init_schema.sql
* [x] Define users, roles, books tables
* [x] Define borrow_transactions table
* [x] Define reservations table
* [x] Add indexes and constraints
* [x] Migrate application.properties → application.yaml with .env support
* [x] Create .env / .env.example / update .gitignore
* [x] Verify project compiles (mvn compile)
* [x] Run initial migration on PostgreSQL — all 8 tables created, roles seeded ✅
* [x] Committed: `feat(db): add flyway v1 schema, jpa entities, and yaml config with .env secret management`

---

## Phase 2 — Authentication System

Status: ✅ COMPLETED
Commit: `feat(auth): implement jwt authentication with register and login endpoints`

Tasks:

* [x] JWT configuration (JwtProperties, JwtService)
* [x] User registration endpoint
* [x] Login endpoint
* [x] Password hashing (BCrypt)
* [x] Role-based authorization (ADMIN, LIBRARIAN, STUDENT)
* [x] GlobalExceptionHandler + ApiErrorResponse
* [x] SecurityConfig with RBAC rules
* [x] Swagger/OpenAPI whitelisted
* [x] Validated against live PostgreSQL ✅

---

## Phase 3 — User & Role Management

Status: ✅ COMPLETED
Commit: `feat(auth): implement jwt authentication with register and login endpoints`

Tasks:

* [x] Role entity and mapping (RoleRepository)
* [x] UserDetailsService implementation
* [x] Role assignment logic (STUDENT by default on register)
* [ ] Admin user management endpoints (deferred to Phase 4+)

---

## Phase 4 — Book Management

Status: 🔄 NEXT UP

Tasks:

* [ ] Book entity creation
* [ ] Book CRUD endpoints
* [ ] Search and filtering
* [ ] Availability tracking

---

## Phase 5 — Borrow System

Status: NOT STARTED

Tasks:

* [ ] Borrow book endpoint
* [ ] Return book endpoint
* [ ] Due date logic (14 days)
* [ ] Copy decrement/increment logic
* [ ] Borrow history

---

## Phase 6 — Reservation System

Status: NOT STARTED

Tasks:

* [ ] Create reservation when book unavailable
* [ ] Prevent duplicate active reservations
* [ ] Reservation status handling
* [ ] Expiration logic (48 hours rule)

---

## Phase 7 — Reporting System

Status: NOT STARTED

Tasks:

* [ ] Most borrowed books report
* [ ] Active users report
* [ ] Overdue books report
* [ ] Monthly statistics

---

## Phase 8 — Dataset Import

Status: NOT STARTED

Tasks:

* [ ] CSV parser implementation
* [ ] Data cleaning (ISBN validation, duplicates removal)
* [ ] Import into PostgreSQL
* [ ] Initial dataset seeding

---

## Phase 9 — Docker Setup

Status: NOT STARTED

Tasks:

* [ ] Backend Dockerfile
* [ ] Frontend Dockerfile (later stage)
* [ ] docker-compose.yml (postgres + backend + frontend)
* [ ] Environment configuration

---

## Phase 10 — Frontend Implementation

Status: NOT STARTED

Tasks:

* [ ] React + Vite setup
* [ ] Authentication pages
* [ ] Dashboard UI
* [ ] Books UI
* [ ] Borrow/Reservation UI
* [ ] Reports UI

---

## Current Focus

Immediate next step:

1. Verify Flyway migration runs cleanly on PostgreSQL
2. Begin Phase 2: JWT Authentication System
3. Provide context files: AI_RULES.md, PACKAGE_STRUCTURE.md, DATABASE_SCHEMA.md, API_SPEC.md

---

## Rules for Progress Tracking

* Every completed task must be marked [x]
* Each commit should reference the current phase
* No phase should start before previous phase is functionally usable
* Always update this file after major feature completion

Context File Usage per Phase
To optimize token usage for local AI models, only provide the required files for the active phase:

Phase 0 (Init): PROJECT_STATUS.md, PROJECT_CONTEXT.md, AI_RULES.md
Phase 1 (Database): AI_RULES.md, DATABASE_SCHEMA.md
Phase 2 & 3 (Auth/User): AI_RULES.md, PACKAGE_STRUCTURE.md, DATABASE_SCHEMA.md, API_SPEC.md
Phase 4, 5, 6 (Core Features): AI_RULES.md, PACKAGE_STRUCTURE.md, DATABASE_SCHEMA.md, API_SPEC.md
Phase 7 (Reports): AI_RULES.md, DATABASE_SCHEMA.md, API_SPEC.md
Phase 8 (Dataset): AI_RULES.md, DATABASE_SCHEMA.md
Phase 9 (Docker): AI_RULES.md, DOCKER_ARCHITECTURE.md
Phase 10 (Frontend): AI_RULES.md, FRONTEND_STRUCTURE.md, API_SPEC.md

---

## Suggested Next Action

Start Phase 1: Database Design & Migration
