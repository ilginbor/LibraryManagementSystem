package com.ilginbor.LibraryManagementSystem.report.service;

import com.ilginbor.LibraryManagementSystem.borrow.entity.BorrowStatus;
import com.ilginbor.LibraryManagementSystem.borrow.repository.BorrowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ReportService {

    private final BorrowRepository borrowRepository;

    public ReportService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    public List<Map<String, Object>> getMostBorrowedBooks() {
        return borrowRepository.findMostBorrowedBooks().stream()
                .map(row -> Map.of(
                        "isbn", row[0],
                        "title", row[1],
                        "borrowCount", ((Number) row[2]).longValue()
                ))
                .toList();
    }

    public List<Map<String, Object>> getMostActiveUsers() {
        return borrowRepository.findMostActiveUsers().stream()
                .map(row -> Map.of(
                        "username", row[0],
                        "borrowCount", ((Number) row[1]).longValue()
                ))
                .toList();
    }

    public List<Map<String, Object>> getOverdueBooks() {
        return borrowRepository.findOverdue(LocalDate.now()).stream()
                .map(bt -> Map.<String, Object>of(
                        "transactionId", bt.getId(),
                        "isbn", bt.getBook().getIsbn(),
                        "title", bt.getBook().getTitle(),
                        "username", bt.getUser().getUsername(),
                        "dueDate", bt.getDueDate(),
                        "status", bt.getStatus()
                ))
                .toList();
    }

    public List<Map<String, Object>> getMonthlyStatistics() {
        return borrowRepository.findMonthlyStatistics().stream()
                .map(row -> {
                    Map<String, Object> entry = new java.util.HashMap<>();
                    entry.put("year",         ((Number) row[0]).intValue());
                    entry.put("month",        ((Number) row[1]).intValue());
                    entry.put("totalBorrows", ((Number) row[2]).longValue());
                    entry.put("totalReturns", ((Number) row[3]).longValue());
                    return entry;
                })
                .toList();
    }
}
