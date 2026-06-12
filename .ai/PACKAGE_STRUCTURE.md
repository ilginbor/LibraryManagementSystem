# Package Structure

Base Package:

com.ilginbor.LibraryManagementSystem

## Shared Packages

config
security
exception
common

## Features

auth
├── controller
├── dto
├── service
├── mapper

user
├── controller
├── dto
├── entity
├── repository
├── service
├── mapper

role
├── entity
├── repository

book
├── controller
├── dto
├── entity
├── repository
├── service
├── mapper

borrow
├── controller
├── dto
├── entity
├── repository
├── service

reservation
├── controller
├── dto
├── entity
├── repository
├── service

report
├── controller
├── service
