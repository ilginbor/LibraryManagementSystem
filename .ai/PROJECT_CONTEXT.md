# Library Management System

## Project Overview

Library Management System is a full-stack application that allows administrators, librarians, and students to manage books, borrowing operations, reservations, user accounts, and reporting.

The system uses the Kaggle Books Dataset as the initial catalog source.

## Technology Stack

### Backend

* Java 26
* Spring Boot 4.0.1
* Spring Data JPA
* Spring Security (JWT)
* PostgreSQL
* Flyway
* Lombok
* Docker

### Frontend

* React
* Vite

## User Roles

### ADMIN

Responsibilities:

* User management
* Role management
* Book management
* Report access
* System administration

### LIBRARIAN

Responsibilities:

* Book management
* Borrow operations
* Return operations
* Reservation management
* Transaction monitoring

### STUDENT

Responsibilities:

* Search books
* View book details
* Borrow books
* Return books
* Create reservations
* View personal transaction history

## Core Features

1. Authentication
2. Authorization (Role-Based Access Control)
3. User Management
4. Book Management
5. Borrow Transactions
6. Return Transactions
7. Reservation Management
8. Reporting and Analytics
9. Dataset Import
10. Docker Deployment

## Main Entities

* User
* Role
* Book
* BorrowTransaction
* Reservation

Optional:

* Notification

## Authentication

* JWT Authentication
* BCrypt Password Hashing

## Dataset Source

Kaggle Books Dataset

Dataset contains:

* ISBN
* Book Title
* Author
* Publisher
* Publication Year

Before import:

* Remove invalid ISBN values
* Remove duplicate records
* Normalize publication years
* Validate required fields

## Business Rules

* A user can borrow a maximum of 5 books simultaneously.
* Borrow duration is 14 days.
* A book can only be borrowed if copies are available.
* Returning a book increases available copies.
* Borrowing a book decreases available copies.
* Reservations are only allowed when no copies are available.
* Duplicate active reservations are not allowed.
* Reservation expires after 48 hours when a book becomes available.

## API Base Path

/api/v1
