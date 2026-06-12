package com.ilginbor.LibraryManagementSystem.borrow.service;

import com.ilginbor.LibraryManagementSystem.book.entity.Book;
import com.ilginbor.LibraryManagementSystem.book.service.BookService;
import com.ilginbor.LibraryManagementSystem.borrow.dto.BorrowRequest;
import com.ilginbor.LibraryManagementSystem.borrow.dto.BorrowResponse;
import com.ilginbor.LibraryManagementSystem.borrow.entity.BorrowStatus;
import com.ilginbor.LibraryManagementSystem.borrow.entity.BorrowTransaction;
import com.ilginbor.LibraryManagementSystem.borrow.repository.BorrowRepository;
import com.ilginbor.LibraryManagementSystem.exception.BusinessException;
import com.ilginbor.LibraryManagementSystem.exception.ResourceNotFoundException;
import com.ilginbor.LibraryManagementSystem.reservation.entity.Reservation;
import com.ilginbor.LibraryManagementSystem.reservation.entity.ReservationStatus;
import com.ilginbor.LibraryManagementSystem.reservation.repository.ReservationRepository;
import com.ilginbor.LibraryManagementSystem.user.entity.User;
import com.ilginbor.LibraryManagementSystem.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BorrowService {

    private static final int MAX_ACTIVE_BORROWS = 5;

    private final BorrowRepository borrowRepository;
    private final BookService bookService;
    private final UserService userService;
    private final ReservationRepository reservationRepository;

    public BorrowService(BorrowRepository borrowRepository, BookService bookService,
                         UserService userService, ReservationRepository reservationRepository) {
        this.borrowRepository = borrowRepository;
        this.bookService = bookService;
        this.userService = userService;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public BorrowResponse borrowBook(BorrowRequest request, String username) {
        User user = userService.getByUsername(username);
        Book book = bookService.findByIsbn(request.isbn());

        if (book.getAvailableCopies() <= 0) {
            throw new BusinessException("No available copies for book: " + book.getTitle());
        }
        long activeBorrows = borrowRepository.countByUserAndStatus(user, BorrowStatus.BORROWED);
        if (activeBorrows >= MAX_ACTIVE_BORROWS) {
            throw new BusinessException("You have reached the maximum of " + MAX_ACTIVE_BORROWS + " borrowed books");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        BorrowTransaction transaction = new BorrowTransaction();
        transaction.setUser(user);
        transaction.setBook(book);
        // @PrePersist sets borrowDate, dueDate and status

        return toResponse(borrowRepository.save(transaction));
    }

    @Transactional
    public BorrowResponse returnBook(Long transactionId, String username) {
        BorrowTransaction transaction = borrowRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + transactionId));

        User currentUser = userService.getByUsername(username);
        boolean isOwner = transaction.getUser().getId().equals(currentUser.getId());
        boolean isPrivileged = currentUser.getRoles().stream()
                .anyMatch(r -> r.getName().name().equals("LIBRARIAN") || r.getName().name().equals("ADMIN"));

        if (!isOwner && !isPrivileged) {
            throw new BusinessException("You are not allowed to return this transaction");
        }
        if (transaction.getStatus() == BorrowStatus.RETURNED) {
            throw new BusinessException("This book has already been returned");
        }

        transaction.setReturnDate(LocalDate.now());
        transaction.setStatus(BorrowStatus.RETURNED);

        Book book = transaction.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        // Complete the oldest active reservation for this book (if any)
        reservationRepository.findOldestActiveByBook(book).ifPresent(reservation -> {
            reservation.setStatus(ReservationStatus.COMPLETED);
            reservationRepository.save(reservation);
        });

        return toResponse(borrowRepository.save(transaction));
    }

    public List<BorrowResponse> getHistory(String username, boolean isPrivileged) {
        if (isPrivileged) {
            return borrowRepository.findAllByOrderByBorrowDateDesc().stream()
                    .map(this::toResponse).toList();
        }
        User user = userService.getByUsername(username);
        return borrowRepository.findByUser(user).stream()
                .map(this::toResponse).toList();
    }

    private BorrowResponse toResponse(BorrowTransaction t) {
        return new BorrowResponse(
                t.getId(),
                t.getBook().getIsbn(),
                t.getBook().getTitle(),
                t.getUser().getUsername(),
                t.getBorrowDate(),
                t.getDueDate(),
                t.getReturnDate(),
                t.getStatus()
        );
    }
}
