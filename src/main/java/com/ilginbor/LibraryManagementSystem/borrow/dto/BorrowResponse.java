package com.ilginbor.LibraryManagementSystem.borrow.dto;

import com.ilginbor.LibraryManagementSystem.borrow.entity.BorrowStatus;

import java.time.LocalDate;

public record BorrowResponse(
        Long id,
        String bookIsbn,
        String bookTitle,
        String username,
        LocalDate borrowDate,
        LocalDate dueDate,
        LocalDate returnDate,
        BorrowStatus status
) {
}
