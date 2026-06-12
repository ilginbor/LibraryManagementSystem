package com.ilginbor.LibraryManagementSystem.reservation.dto;

import jakarta.validation.constraints.NotBlank;

public record ReservationRequest(
        @NotBlank(message = "ISBN is required")
        String isbn
) {
}
