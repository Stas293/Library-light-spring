package org.springapp.service;

import org.springapp.models.Book;
import org.springapp.models.Human;
import org.springapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BookService {
    private final BookRepository bookRepository;


    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void save(Book book) {
        bookRepository.save(book);
    }

    public Book getBook(long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void release(long id) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setOwner(null);
            bookRepository.save(book);
        }
    }

    public void take(long id, Human person) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setOwner(person);
            book.setDateRequested(new Date());
            bookRepository.save(book);
        }
    }

    public void delete(long id) {
        bookRepository.deleteById(id);
    }

    public void update(Book book) {
        bookRepository.save(book);
    }

    public Page<Book> getPage(int pageNum, int booksPerPage, String sortField) {
        return bookRepository.findAll(PageRequest.of(pageNum, booksPerPage, Sort.by(sortField)));
    }

    public Page<Book> search(String query, int pageNum, int booksPerPage, String sortField) {
        return bookRepository.findByTitleContainingIgnoreCase(query, PageRequest.of(pageNum, booksPerPage, Sort.by(sortField)));
    }
}
