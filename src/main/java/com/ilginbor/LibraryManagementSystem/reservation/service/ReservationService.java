package com.ilginbor.LibraryManagementSystem.reservation.service;

import com.ilginbor.LibraryManagementSystem.book.entity.Book;
import com.ilginbor.LibraryManagementSystem.book.service.BookService;
import com.ilginbor.LibraryManagementSystem.exception.BusinessException;
import com.ilginbor.LibraryManagementSystem.exception.ResourceNotFoundException;
import com.ilginbor.LibraryManagementSystem.reservation.dto.ReservationRequest;
import com.ilginbor.LibraryManagementSystem.reservation.dto.ReservationResponse;
import com.ilginbor.LibraryManagementSystem.reservation.entity.Reservation;
import com.ilginbor.LibraryManagementSystem.reservation.entity.ReservationStatus;
import com.ilginbor.LibraryManagementSystem.reservation.repository.ReservationRepository;
import com.ilginbor.LibraryManagementSystem.user.entity.User;
import com.ilginbor.LibraryManagementSystem.user.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookService bookService;
    private final UserService userService;

    public ReservationService(ReservationRepository reservationRepository,
                               BookService bookService,
                               UserService userService) {
        this.reservationRepository = reservationRepository;
        this.bookService = bookService;
        this.userService = userService;
    }

    @Transactional
    public ReservationResponse create(ReservationRequest request, String username) {
        User user = userService.getByUsername(username);
        Book book = bookService.findByIsbn(request.isbn());

        if (book.getAvailableCopies() > 0) {
            throw new BusinessException("Book has available copies — please borrow it instead of reserving");
        }
        if (reservationRepository.existsByUserAndBookAndStatus(user, book, ReservationStatus.ACTIVE)) {
            throw new BusinessException("You already have an active reservation for: " + book.getTitle());
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        // @PrePersist sets reservationDate and ACTIVE status

        return toResponse(reservationRepository.save(reservation));
    }

    public List<ReservationResponse> getReservations(String username, boolean isPrivileged) {
        if (isPrivileged) {
            return reservationRepository.findAllByOrderByReservationDateDesc().stream()
                    .map(this::toResponse).toList();
        }
        User user = userService.getByUsername(username);
        return reservationRepository.findByUser(user).stream()
                .map(this::toResponse).toList();
    }

    @Transactional
    public void cancel(Long id, String username) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found: " + id));

        User currentUser = userService.getByUsername(username);
        boolean isOwner = reservation.getUser().getId().equals(currentUser.getId());
        boolean isPrivileged = currentUser.getRoles().stream()
                .anyMatch(r -> r.getName().name().equals("LIBRARIAN") || r.getName().name().equals("ADMIN"));

        if (!isOwner && !isPrivileged) {
            throw new BusinessException("You are not allowed to cancel this reservation");
        }
        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new BusinessException("Only ACTIVE reservations can be cancelled");
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

    /**
     * Expires reservations that have been ACTIVE for more than 48 hours.
     * Runs every hour.
     */
    @Scheduled(fixedRate = 3_600_000)
    @Transactional
    public void expireStaleReservations() {
        LocalDateTime expiryTime = LocalDateTime.now().minusHours(48);
        List<Reservation> expired = reservationRepository.findExpiredReservations(expiryTime);
        expired.forEach(r -> r.setStatus(ReservationStatus.CANCELLED));
        reservationRepository.saveAll(expired);
    }

    private ReservationResponse toResponse(Reservation r) {
        return new ReservationResponse(
                r.getId(),
                r.getBook().getIsbn(),
                r.getBook().getTitle(),
                r.getUser().getUsername(),
                r.getReservationDate(),
                r.getStatus()
        );
    }
}
