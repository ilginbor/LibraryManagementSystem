package com.ilginbor.LibraryManagementSystem.book.dto;

import jakarta.validation.constraints.*;

public record BookRequest(
        @NotBlank(message = "ISBN is required")
        @Size(max = 20, message = "ISBN must be at most 20 characters")
        String isbn,

        @NotBlank(message = "Title is required")
        @Size(max = 500, message = "Title must be at most 500 characters")
        String title,

        @NotBlank(message = "Author is required")
        @Size(max = 255, message = "Author must be at most 255 characters")
        String author,

        @Size(max = 255, message = "Publisher must be at most 255 characters")
        String publisher,

        @Min(value = 1000, message = "Publish year must be at least 1000")
        @Max(value = 2100, message = "Publish year must be at most 2100")
        Integer publishYear,

        @NotNull(message = "Total copies is required")
        @Min(value = 1, message = "At least 1 copy is required")
        Integer totalCopies
) {
}
