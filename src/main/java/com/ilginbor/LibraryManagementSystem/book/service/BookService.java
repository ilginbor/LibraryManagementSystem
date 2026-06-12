package com.ilginbor.LibraryManagementSystem.book.service;

import com.ilginbor.LibraryManagementSystem.book.dto.BookRequest;
import com.ilginbor.LibraryManagementSystem.book.dto.BookResponse;
import com.ilginbor.LibraryManagementSystem.book.entity.Book;
import com.ilginbor.LibraryManagementSystem.book.mapper.BookMapper;
import com.ilginbor.LibraryManagementSystem.book.repository.BookRepository;
import com.ilginbor.LibraryManagementSystem.exception.BusinessException;
import com.ilginbor.LibraryManagementSystem.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public Page<BookResponse> searchBooks(String search, String author, String publisher, Pageable pageable) {
        return bookRepository.searchBooks(search, author, publisher, pageable)
                .map(bookMapper::toResponse);
    }

    public BookResponse getByIsbn(String isbn) {
        return bookMapper.toResponse(findByIsbn(isbn));
    }

    @Transactional
    public BookResponse create(BookRequest request) {
        if (bookRepository.existsByIsbn(request.isbn())) {
            throw new BusinessException("Book with ISBN " + request.isbn() + " already exists");
        }
        Book book = new Book();
        book.setIsbn(request.isbn());
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setPublisher(request.publisher());
        book.setPublishYear(request.publishYear());
        book.setTotalCopies(request.totalCopies());
        book.setAvailableCopies(request.totalCopies());
        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Transactional
    public BookResponse update(String isbn, BookRequest request) {
        Book book = findByIsbn(isbn);
        if (!book.getIsbn().equals(request.isbn()) && bookRepository.existsByIsbn(request.isbn())) {
            throw new BusinessException("Book with ISBN " + request.isbn() + " already exists");
        }
        int copiesDiff = request.totalCopies() - book.getTotalCopies();
        book.setIsbn(request.isbn());
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setPublisher(request.publisher());
        book.setPublishYear(request.publishYear());
        book.setTotalCopies(request.totalCopies());
        book.setAvailableCopies(Math.max(0, book.getAvailableCopies() + copiesDiff));
        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Transactional
    public void delete(String isbn) {
        bookRepository.delete(findByIsbn(isbn));
    }

    // Package-visible for use by BorrowService and ReservationService
    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));
    }
}
