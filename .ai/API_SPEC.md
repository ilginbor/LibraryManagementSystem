# API Specification

Base Path:

/api/v1

## Authentication

POST /auth/register
POST /auth/login
POST /auth/logout

Public:

* register
* login

---

## Books

GET /books
GET /books/{isbn}

Query Parameters:

* page
* size
* search
* author
* publisher

Roles:

* STUDENT
* LIBRARIAN
* ADMIN

Create:

POST /books

Roles:

* LIBRARIAN
* ADMIN

Update:

PUT /books/{isbn}

Roles:

* LIBRARIAN
* ADMIN

Delete:

DELETE /books/{isbn}

Roles:

* LIBRARIAN
* ADMIN

---

## Borrow

POST /borrow

Request:

* isbn

Rules:

* Available copy required
* Due date = borrow date + 14 days

POST /return/{transactionId}

GET /borrow/history

Roles:

* STUDENT (own history)
* LIBRARIAN
* ADMIN

---

## Reservations

POST /reservations

Rules:

* Book must be unavailable
* Duplicate active reservation forbidden

GET /reservations

DELETE /reservations/{id}

---

## Reports

GET /reports/most-borrowed

GET /reports/active-users

GET /reports/overdue-books

GET /reports/monthly-statistics

Roles:

* ADMIN
