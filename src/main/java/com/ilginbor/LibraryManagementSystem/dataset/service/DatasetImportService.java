package com.ilginbor.LibraryManagementSystem.dataset.service;

import com.ilginbor.LibraryManagementSystem.book.entity.Book;
import com.ilginbor.LibraryManagementSystem.book.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.FileInputStream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DatasetImportService {

    private static final int BATCH_SIZE = 500;

    private final BookRepository bookRepository;

    public DatasetImportService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Imports the Kaggle BX-Books CSV dataset.
     * Expected format (semicolon-delimited, double-quoted fields):
     * ISBN;"Book-Title";"Book-Author";"Year-Of-Publication";"Publisher";...
     *
     * @param file uploaded CSV file
     * @return number of books successfully imported
     */
    public int importBooks(String filePath) {
        int imported = 0;
        int skipped = 0;
        List<Book> batch = new ArrayList<>(BATCH_SIZE);

        Set<String> seenIsbns = new HashSet<>(bookRepository.findAllIsbns());

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                // Skip BOM and header row
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                if (line.isBlank()) continue;

                String[] fields = parseCsvLine(line);
                if (fields.length < 5) { skipped++; continue; }

                String isbn   = clean(fields[0]);
                String title  = clean(fields[1]);
                String author = clean(fields[2]);
                String yearRaw = clean(fields[3]);
                String publisher = clean(fields[4]);

                // Data cleaning rules
                if (!isValidIsbn(isbn))     { skipped++; continue; }
                if (title.isBlank())        { skipped++; continue; }
                if (author.isBlank())       { skipped++; continue; }
                if (seenIsbns.contains(isbn)) { skipped++; continue; }
                
                seenIsbns.add(isbn);

                Integer publishYear = parseYear(yearRaw);

                Book book = new Book();
                book.setIsbn(isbn);
                book.setTitle(title.length() > 500 ? title.substring(0, 500) : title);
                book.setAuthor(author.length() > 255 ? author.substring(0, 255) : author);
                book.setPublisher(publisher.length() > 255 ? publisher.substring(0, 255) : publisher);
                book.setPublishYear(publishYear);
                book.setTotalCopies(1);
                book.setAvailableCopies(1);

                batch.add(book);
                imported++;

                if (batch.size() >= BATCH_SIZE) {
                    bookRepository.saveAll(batch);
                    batch.clear();
                }
            }

            if (!batch.isEmpty()) {
                bookRepository.saveAll(batch);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to import dataset: " + e.getMessage(), e);
        }

        return imported;
    }

    /**
     * Parses a single semicolon-delimited CSV line, handling double-quoted fields.
     */
    private String[] parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        // Strip UTF-8 BOM if present on first field
        if (line.startsWith("\uFEFF")) {
            line = line.substring(1);
        }

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++; // skip escaped quote
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ';' && !inQuotes) {
                fields.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        fields.add(current.toString());
        return fields.toArray(new String[0]);
    }

    private String clean(String value) {
        return value == null ? "" : value.trim().replaceAll("^\"|\"$", "");
    }

    private boolean isValidIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) return false;
        // Basic sanity: ISBN-10 or ISBN-13 length after removing hyphens
        String normalized = isbn.replace("-", "").replace(" ", "");
        return normalized.matches("[0-9X]{10}|[0-9]{13}");
    }

    private Integer parseYear(String yearRaw) {
        try {
            int year = Integer.parseInt(yearRaw);
            return (year >= 1000 && year <= 2100) ? year : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
