package com.ilginbor.LibraryManagementSystem.book.dto;

import java.time.LocalDateTime;

public record BookResponse(
        Long id,
        String isbn,
        String title,
        String author,
        String publisher,
        Integer publishYear,
        Integer totalCopies,
        Integer availableCopies,
        LocalDateTime createdAt
) {
}
