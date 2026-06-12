package com.ilginbor.LibraryManagementSystem.borrow.controller;

import com.ilginbor.LibraryManagementSystem.borrow.dto.BorrowRequest;
import com.ilginbor.LibraryManagementSystem.borrow.dto.BorrowResponse;
import com.ilginbor.LibraryManagementSystem.borrow.service.BorrowService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/borrow")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('STUDENT','LIBRARIAN','ADMIN')")
    public ResponseEntity<BorrowResponse> borrow(@Valid @RequestBody BorrowRequest request,
                                                  @AuthenticationPrincipal UserDetails currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(borrowService.borrowBook(request, currentUser.getUsername()));
    }

    @PostMapping("/return/{transactionId}")
    @PreAuthorize("hasAnyRole('STUDENT','LIBRARIAN','ADMIN')")
    public ResponseEntity<BorrowResponse> returnBook(@PathVariable Long transactionId,
                                                      @AuthenticationPrincipal UserDetails currentUser) {
        return ResponseEntity.ok(borrowService.returnBook(transactionId, currentUser.getUsername()));
    }

    @GetMapping("/history")
    @PreAuthorize("hasAnyRole('STUDENT','LIBRARIAN','ADMIN')")
    public ResponseEntity<List<BorrowResponse>> history(@AuthenticationPrincipal UserDetails currentUser) {
        boolean privileged = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_LIBRARIAN") || a.getAuthority().equals("ROLE_ADMIN"));
        return ResponseEntity.ok(borrowService.getHistory(currentUser.getUsername(), privileged));
    }
}
