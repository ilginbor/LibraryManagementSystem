package com.ilginbor.LibraryManagementSystem.book.mapper;

import com.ilginbor.LibraryManagementSystem.book.dto.BookResponse;
import com.ilginbor.LibraryManagementSystem.book.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublisher(),
                book.getPublishYear(),
                book.getTotalCopies(),
                book.getAvailableCopies(),
                book.getCreatedAt()
        );
    }
}
