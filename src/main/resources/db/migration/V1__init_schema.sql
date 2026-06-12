-- =============================================
-- V1__init_schema.sql
-- Initial database schema for Library Management System
-- =============================================

-- =============================================
-- ENUMS
-- =============================================
CREATE TYPE borrow_status AS ENUM ('BORROWED', 'RETURNED', 'OVERDUE');
CREATE TYPE reservation_status AS ENUM ('ACTIVE', 'COMPLETED', 'CANCELLED');

-- =============================================
-- TABLE: roles
-- =============================================
CREATE TABLE roles (
    id   BIGSERIAL    PRIMARY KEY,
    name VARCHAR(20)  NOT NULL UNIQUE
);

-- Seed the three allowed roles immediately
INSERT INTO roles (name) VALUES ('ADMIN'), ('LIBRARIAN'), ('STUDENT');

-- =============================================
-- TABLE: users
-- =============================================
CREATE TABLE users (
    id            BIGSERIAL     PRIMARY KEY,
    username      VARCHAR(50)   NOT NULL UNIQUE,
    email         VARCHAR(150)  NOT NULL UNIQUE,
    password_hash VARCHAR(255)  NOT NULL,
    created_at    TIMESTAMP     NOT NULL DEFAULT NOW()
);

-- =============================================
-- TABLE: user_roles  (User <-> Role join table)
-- =============================================
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE RESTRICT,
    PRIMARY KEY (user_id, role_id)
);

-- =============================================
-- TABLE: books
-- =============================================
CREATE TABLE books (
    id               BIGSERIAL    PRIMARY KEY,
    isbn             VARCHAR(20)  NOT NULL UNIQUE,
    title            VARCHAR(500) NOT NULL,
    author           VARCHAR(255) NOT NULL,
    publisher        VARCHAR(255),
    publish_year     INTEGER,
    total_copies     INTEGER      NOT NULL DEFAULT 1 CHECK (total_copies >= 0),
    available_copies INTEGER      NOT NULL DEFAULT 1 CHECK (available_copies >= 0),
    created_at       TIMESTAMP    NOT NULL DEFAULT NOW(),
    CONSTRAINT available_lte_total CHECK (available_copies <= total_copies)
);

-- =============================================
-- TABLE: borrow_transactions
-- =============================================
CREATE TABLE borrow_transactions (
    id          BIGSERIAL    PRIMARY KEY,
    user_id     BIGINT       NOT NULL REFERENCES users(id) ON DELETE RESTRICT,
    book_id     BIGINT       NOT NULL REFERENCES books(id) ON DELETE RESTRICT,
    borrow_date DATE         NOT NULL DEFAULT CURRENT_DATE,
    due_date    DATE         NOT NULL,
    return_date DATE,
    status      borrow_status NOT NULL DEFAULT 'BORROWED'
);

-- =============================================
-- TABLE: reservations
-- =============================================
CREATE TABLE reservations (
    id               BIGSERIAL          PRIMARY KEY,
    user_id          BIGINT             NOT NULL REFERENCES users(id) ON DELETE RESTRICT,
    book_id          BIGINT             NOT NULL REFERENCES books(id) ON DELETE RESTRICT,
    reservation_date TIMESTAMP          NOT NULL DEFAULT NOW(),
    status           reservation_status NOT NULL DEFAULT 'ACTIVE'
);

-- Prevent duplicate ACTIVE reservations for the same user+book
CREATE UNIQUE INDEX uq_active_reservation
    ON reservations (user_id, book_id)
    WHERE status = 'ACTIVE';

-- =============================================
-- TABLE: notifications  (optional)
-- =============================================
CREATE TABLE notifications (
    id         BIGSERIAL    PRIMARY KEY,
    user_id    BIGINT       NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    message    TEXT         NOT NULL,
    is_read    BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- =============================================
-- INDEXES
-- =============================================

-- Users
CREATE INDEX idx_users_email    ON users(email);
CREATE INDEX idx_users_username ON users(username);

-- Books
CREATE INDEX idx_books_isbn     ON books(isbn);
CREATE INDEX idx_books_author   ON books(author);
CREATE INDEX idx_books_title    ON books(title);

-- Borrow transactions
CREATE INDEX idx_borrow_user_id  ON borrow_transactions(user_id);
CREATE INDEX idx_borrow_book_id  ON borrow_transactions(book_id);
CREATE INDEX idx_borrow_status   ON borrow_transactions(status);
CREATE INDEX idx_borrow_due_date ON borrow_transactions(due_date);

-- Reservations
CREATE INDEX idx_reservation_user_id ON reservations(user_id);
CREATE INDEX idx_reservation_book_id ON reservations(book_id);
CREATE INDEX idx_reservation_status  ON reservations(status);

-- Notifications
CREATE INDEX idx_notification_user_id ON notifications(user_id);
