package com.ilginbor.LibraryManagementSystem.dataset;

import com.ilginbor.LibraryManagementSystem.book.repository.BookRepository;
import com.ilginbor.LibraryManagementSystem.dataset.service.DatasetImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseSeeder.class);

    private final BookRepository bookRepository;
    private final DatasetImportService datasetImportService;

    public DatabaseSeeder(BookRepository bookRepository, DatasetImportService datasetImportService) {
        this.bookRepository = bookRepository;
        this.datasetImportService = datasetImportService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (bookRepository.count() == 0) {
            String datasetPath = "BooksDataset/books_data/books.csv";
            File datasetFile = new File(datasetPath);
            
            if (datasetFile.exists()) {
                log.info("Books table is empty. Starting dataset import from {}", datasetPath);
                int imported = datasetImportService.importBooks(datasetPath);
                log.info("Successfully imported {} books into the database.", imported);
            } else {
                log.warn("Dataset file not found at {}. Skipping dataset import.", datasetFile.getAbsolutePath());
            }
        } else {
            log.info("Books table already contains data. Skipping dataset import.");
        }
    }
}
