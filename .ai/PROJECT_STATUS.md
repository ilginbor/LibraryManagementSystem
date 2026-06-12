# Project Status

## Project Name

Library Management System

## Current Phase

Phase 0 — Project Initialization

## Overall Progress

### Setup

* [x] Spring Boot project created
* [x] Dependencies selected (JPA, Security, PostgreSQL, Flyway, Lombok, Validation, Docker)
* [ ] Git repository initialized properly
* [ ] README created
* [ ] .ai context system created

---

### Phase Breakdown

## Phase 0 — Project Initialization

Status: IN PROGRESS

Tasks:

* [x] Create Spring Boot base project
* [ ] Initialize Git with conventional commit workflow
* [ ] Create .ai context files
* [ ] Define package structure
* [ ] Create initial README

---

## Phase 1 — Database Design & Migration

Status: NOT STARTED

Tasks:

* [ ] Create Flyway migration V1__init_schema.sql
* [ ] Define users, roles, books tables
* [ ] Define borrow_transactions table
* [ ] Define reservations table
* [ ] Add indexes and constraints
* [ ] Run initial migration on PostgreSQL

---

## Phase 2 — Authentication System

Status: NOT STARTED

Tasks:

* [ ] JWT configuration
* [ ] User registration endpoint
* [ ] Login endpoint
* [ ] Password hashing (BCrypt)
* [ ] Role-based authorization (ADMIN, LIBRARIAN, STUDENT)

---

## Phase 3 — User & Role Management

Status: NOT STARTED

Tasks:

* [ ] Role entity and mapping
* [ ] User CRUD operations
* [ ] Role assignment logic
* [ ] Admin user management endpoints

---

## Phase 4 — Book Management

Status: NOT STARTED

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

1. Create `DATABASE_SCHEMA.md`
2. Create Flyway migration (V1__init_schema.sql)
3. Implement base entities

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
