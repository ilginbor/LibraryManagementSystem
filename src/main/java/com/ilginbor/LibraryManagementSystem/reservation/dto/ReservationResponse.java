package com.ilginbor.LibraryManagementSystem.reservation.dto;

import com.ilginbor.LibraryManagementSystem.reservation.entity.ReservationStatus;

import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        String bookIsbn,
        String bookTitle,
        String username,
        LocalDateTime reservationDate,
        ReservationStatus status
) {
}
