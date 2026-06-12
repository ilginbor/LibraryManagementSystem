package com.ilginbor.LibraryManagementSystem.borrow.dto;

import jakarta.validation.constraints.NotBlank;

public record BorrowRequest(
        @NotBlank(message = "ISBN is required")
        String isbn
) {
}
