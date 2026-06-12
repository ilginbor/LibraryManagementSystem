package com.ilginbor.LibraryManagementSystem.reservation.entity;

import com.ilginbor.LibraryManagementSystem.book.entity.Book;
import com.ilginbor.LibraryManagementSystem.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "reservation_date", nullable = false, updatable = false)
    private LocalDateTime reservationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "reservation_status")
    private ReservationStatus status;

    @PrePersist
    protected void onCreate() {
        this.reservationDate = LocalDateTime.now();
        this.status = ReservationStatus.ACTIVE;
    }
}
