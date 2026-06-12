package com.ilginbor.LibraryManagementSystem.book.entity;

import com.ilginbor.LibraryManagementSystem.borrow.entity.BorrowTransaction;
import com.ilginbor.LibraryManagementSystem.reservation.entity.Reservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isbn", nullable = false, unique = true, length = 20)
    private String isbn;

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "author", nullable = false, length = 255)
    private String author;

    @Column(name = "publisher", length = 255)
    private String publisher;

    @Column(name = "publish_year")
    private Integer publishYear;

    @Column(name = "total_copies", nullable = false)
    private Integer totalCopies;

    @Column(name = "available_copies", nullable = false)
    private Integer availableCopies;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BorrowTransaction> borrowTransactions;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
