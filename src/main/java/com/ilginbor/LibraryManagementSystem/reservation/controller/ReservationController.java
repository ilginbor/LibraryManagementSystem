package com.ilginbor.LibraryManagementSystem.reservation.controller;

import com.ilginbor.LibraryManagementSystem.reservation.dto.ReservationRequest;
import com.ilginbor.LibraryManagementSystem.reservation.dto.ReservationResponse;
import com.ilginbor.LibraryManagementSystem.reservation.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('STUDENT','LIBRARIAN','ADMIN')")
    public ResponseEntity<ReservationResponse> create(@Valid @RequestBody ReservationRequest request,
                                                       @AuthenticationPrincipal UserDetails currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationService.create(request, currentUser.getUsername()));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT','LIBRARIAN','ADMIN')")
    public ResponseEntity<List<ReservationResponse>> getAll(@AuthenticationPrincipal UserDetails currentUser) {
        boolean privileged = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_LIBRARIAN") || a.getAuthority().equals("ROLE_ADMIN"));
        return ResponseEntity.ok(reservationService.getReservations(currentUser.getUsername(), privileged));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT','LIBRARIAN','ADMIN')")
    public ResponseEntity<Void> cancel(@PathVariable Long id,
                                        @AuthenticationPrincipal UserDetails currentUser) {
        reservationService.cancel(id, currentUser.getUsername());
        return ResponseEntity.noContent().build();
    }
}
