package org.springapp.service;

import org.springapp.dao.BookDAO;
import org.springapp.models.Book;
import org.springapp.models.Human;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookDAO bookDAO;


    @Autowired
    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public List<Book> getList() {
        return bookDAO.getList();
    }

    public void save(Book book) {
        bookDAO.save(book);
    }

    public Book getBook(int id) {
        return bookDAO.get(id).orElse(null);
    }

    public Human getOwner(int id) {
        return bookDAO.getHumanByBookId(id).orElse(null);
    }

    public void release(int id) {
        bookDAO.release(id);
    }

    public void take(int id, Human person) {
        bookDAO.take(id, person.getId());
    }

    public void delete(int id) {
        bookDAO.delete(id);
    }

    public void update(Book book) {
        bookDAO.update(book);
    }
}
