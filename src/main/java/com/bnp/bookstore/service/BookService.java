package com.bnp.bookstore.service;

import com.bnp.bookstore.entity.Book;
import com.bnp.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks(boolean includeOutOfStock) {
        return includeOutOfStock ? bookRepository.findAll() : bookRepository.findAllStock();
    }

    public void updateBook(Book book, Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book dbBook = bookOptional.get();
            dbBook.setStock(book.getStock());
            bookRepository.save(dbBook);
        } else {
            throw new RuntimeException("Book not found with id: " + bookId);
        }
    }

    public Optional<Book> getBookById(Long id) {
        return Optional.of(bookRepository.getReferenceById(id));
    }

    public List<Book> getAllBooks(List<Long> bookIds) {
        return bookRepository.findAllById(bookIds);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
