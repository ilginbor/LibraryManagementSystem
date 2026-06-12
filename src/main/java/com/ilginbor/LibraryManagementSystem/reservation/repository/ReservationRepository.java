package com.ilginbor.LibraryManagementSystem.reservation.repository;

import com.ilginbor.LibraryManagementSystem.book.entity.Book;
import com.ilginbor.LibraryManagementSystem.reservation.entity.Reservation;
import com.ilginbor.LibraryManagementSystem.reservation.entity.ReservationStatus;
import com.ilginbor.LibraryManagementSystem.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByUserAndBookAndStatus(User user, Book book, ReservationStatus status);

    List<Reservation> findByUser(User user);

    List<Reservation> findAllByOrderByReservationDateDesc();

    // Pick the earliest active reservation for a book (FIFO queue)
    @Query("SELECT r FROM Reservation r WHERE r.book = :book AND r.status = 'ACTIVE' " +
            "ORDER BY r.reservationDate ASC")
    Optional<Reservation> findOldestActiveByBook(@Param("book") Book book);

    // For expiration job (48-hour rule)
    @Query("SELECT r FROM Reservation r WHERE r.status = 'ACTIVE' AND r.reservationDate < :expiryTime")
    List<Reservation> findExpiredReservations(@Param("expiryTime") LocalDateTime expiryTime);
}
