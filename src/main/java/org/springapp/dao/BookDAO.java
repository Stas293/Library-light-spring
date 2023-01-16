package org.springapp.dao;

import org.springapp.models.Book;
import org.springapp.models.Human;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getList() {
        return jdbcTemplate.query("SELECT * FROM book order by id", new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, author, published) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getPublished());
    }

    public List<Book> getBooksByHumanId(long id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE owner_id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> get(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream()
                .findAny();
    }

    public Optional<Human> getHumanByBookId(int id) {
        return jdbcTemplate.query("SELECT * FROM human WHERE id = (SELECT owner_id FROM book WHERE id = ?)",
                new Object[]{id}, new BeanPropertyRowMapper<>(Human.class))
                .stream()
                .findAny();
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE book SET owner_id = NULL WHERE id = ?", id);
    }

    public void take(int id, long personId) {
        jdbcTemplate.update("UPDATE book SET owner_id = ? WHERE id = ?", personId, id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    public void update(Book book) {
        jdbcTemplate.update("UPDATE book SET title = ?, author = ?, published = ? WHERE id = ?",
                book.getTitle(), book.getAuthor(), book.getPublished(), book.getId());
    }
}
