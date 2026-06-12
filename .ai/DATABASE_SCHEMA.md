# Database Schema

## Role

Fields:

* id : Long
* name : String

Relationships:

* Many-to-Many with User

Allowed Values:

* ADMIN
* LIBRARIAN
* STUDENT

---

## User

Fields:

* id : Long
* username : String
* email : String
* passwordHash : String
* createdAt : LocalDateTime

Relationships:

* Many-to-Many Role
* One-to-Many BorrowTransaction
* One-to-Many Reservation
* One-to-Many Notification

---

## Book

Fields:

* isbn : String
* title : String
* author : String
* publisher : String
* publishYear : Integer
* totalCopies : Integer
* availableCopies : Integer
* createdAt : LocalDateTime

Relationships:

* One-to-Many BorrowTransaction
* One-to-Many Reservation

---

## BorrowTransaction

Fields:

* id : Long
* borrowDate : LocalDate
* dueDate : LocalDate
* returnDate : LocalDate
* status : BorrowStatus

Relationships:

* Many-to-One User
* Many-to-One Book

Allowed Status Values:

* BORROWED
* RETURNED
* OVERDUE

---

## Reservation

Fields:

* id : Long
* reservationDate : LocalDateTime
* status : ReservationStatus

Relationships:

* Many-to-One User
* Many-to-One Book

Allowed Status Values:

* ACTIVE
* COMPLETED
* CANCELLED

---

## Notification (Optional)

Fields:

* id : Long
* message : String
* isRead : Boolean
* createdAt : LocalDateTime

Relationships:

* Many-to-One User
