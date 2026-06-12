package com.ilginbor.LibraryManagementSystem.borrow.entity;

import com.ilginbor.LibraryManagementSystem.book.entity.Book;
import com.ilginbor.LibraryManagementSystem.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "borrow_transactions")
@Getter
@Setter
@NoArgsConstructor
public class BorrowTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "borrow_status")
    private BorrowStatus status;

    @PrePersist
    protected void onCreate() {
        this.borrowDate = LocalDate.now();
        this.dueDate = this.borrowDate.plusDays(14);
        this.status = BorrowStatus.BORROWED;
    }
}
