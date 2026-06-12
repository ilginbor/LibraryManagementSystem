package com.ilginbor.LibraryManagementSystem.report.controller;

import com.ilginbor.LibraryManagementSystem.report.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reports")
@PreAuthorize("hasRole('ADMIN')")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/most-borrowed")
    public ResponseEntity<List<Map<String, Object>>> mostBorrowed() {
        return ResponseEntity.ok(reportService.getMostBorrowedBooks());
    }

    @GetMapping("/active-users")
    public ResponseEntity<List<Map<String, Object>>> activeUsers() {
        return ResponseEntity.ok(reportService.getMostActiveUsers());
    }

    @GetMapping("/overdue-books")
    public ResponseEntity<List<Map<String, Object>>> overdueBooks() {
        return ResponseEntity.ok(reportService.getOverdueBooks());
    }

    @GetMapping("/monthly-statistics")
    public ResponseEntity<List<Map<String, Object>>> monthlyStatistics() {
        return ResponseEntity.ok(reportService.getMonthlyStatistics());
    }
}
