# AI Development Rules

## General Rules

* Use Java 26.
* Use Spring Boot 4.0.1.
* Use PostgreSQL.
* Use Flyway for database migrations.
* Use JWT authentication.
* Use BCrypt for password hashing.
* Use Lombok where appropriate.
* Use Jakarta Validation annotations.

## Architecture Rules

Use Feature-Based Architecture.

Each feature must contain only the folders it needs.

Example:

feature/

* controller
* service
* repository
* entity
* dto
* mapper

Shared packages:

* config
* security
* exception
* common

## Package Structure

Base package:

com.ilginbor.LibraryManagementSystem

## Coding Rules

* Never write business logic inside controllers.
* Controllers must communicate only with services.
* Repositories must contain only database access logic.
* Never expose entities directly through API responses.
* Use DTOs for all request and response payloads.
* Use constructor injection only.
* Never use field injection.
* Follow REST conventions.
* Keep methods focused and reasonably small.
* Validate all incoming requests.

## Database Rules

* Use Flyway migrations for schema management.
* Never use Hibernate auto schema generation.
* Never use ddl-auto=create or ddl-auto=update.
* ISBN is the primary identifier of books.
* Use proper foreign key constraints and indexes.

## Security Rules

Public endpoints:

* POST /api/v1/auth/register
* POST /api/v1/auth/login

Role permissions:

### STUDENT

Can:

* View books
* Borrow books
* Return books
* Create reservations
* View own transaction history

### LIBRARIAN

All STUDENT permissions plus:

* Create books
* Update books
* Delete books
* View all transactions
* Manage reservations

### ADMIN

All LIBRARIAN permissions plus:

* Manage users
* Manage roles
* Access reports
* Perform administrative actions

## Output Rules

* Generate only the files requested.
* Do not modify unrelated files.
* Do not rewrite existing files unless requested.
* Include required imports.
* Do not generate placeholder code unless requested.
* Prefer production-ready implementations.
* Continuous Documentation: At the end of each phase or significant task, evaluate if README.md or any files within the .ai directory need updates to reflect the new state. If so, provide the updated content automatically.

## Git Rules

Use Conventional Commits.

Format:

type(scope): description

Examples:

feat(auth): implement JWT authentication

feat(book): add book search endpoint

fix(borrow): prevent borrowing unavailable books

refactor(user): simplify role assignment logic

docs(readme): update setup instructions

test(book): add repository tests

chore(docker): configure postgres container

When completing a task, always suggest an appropriate commit message.
