package com.ilginbor.LibraryManagementSystem.borrow.repository;

import com.ilginbor.LibraryManagementSystem.borrow.entity.BorrowStatus;
import com.ilginbor.LibraryManagementSystem.borrow.entity.BorrowTransaction;
import com.ilginbor.LibraryManagementSystem.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BorrowRepository extends JpaRepository<BorrowTransaction, Long> {

    List<BorrowTransaction> findByUser(User user);

    List<BorrowTransaction> findAllByOrderByBorrowDateDesc();

    long countByUserAndStatus(User user, BorrowStatus status);

    @Query("SELECT bt FROM BorrowTransaction bt WHERE bt.status = 'BORROWED' AND bt.dueDate < :today")
    List<BorrowTransaction> findOverdue(@Param("today") LocalDate today);

    // Report: most borrowed books
    @Query("SELECT bt.book.isbn, bt.book.title, COUNT(bt) AS borrowCount " +
            "FROM BorrowTransaction bt GROUP BY bt.book.isbn, bt.book.title " +
            "ORDER BY COUNT(bt) DESC")
    List<Object[]> findMostBorrowedBooks();

    // Report: most active users
    @Query("SELECT bt.user.username, COUNT(bt) AS borrowCount " +
            "FROM BorrowTransaction bt GROUP BY bt.user.username " +
            "ORDER BY COUNT(bt) DESC")
    List<Object[]> findMostActiveUsers();

    // Report: monthly statistics
    @Query(value = """
            SELECT EXTRACT(YEAR FROM borrow_date)  AS year,
                   EXTRACT(MONTH FROM borrow_date) AS month,
                   COUNT(*)                         AS total_borrows,
                   SUM(CASE WHEN status = 'RETURNED' THEN 1 ELSE 0 END) AS total_returns
            FROM borrow_transactions
            GROUP BY year, month
            ORDER BY year DESC, month DESC
            """, nativeQuery = true)
    List<Object[]> findMonthlyStatistics();
}
