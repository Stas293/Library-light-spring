package org.springapp.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Book {
    private long id;
    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")
    private String title;
    @NotEmpty(message = "Author cannot be empty")
    @Size(min = 2, max = 255, message = "Author must be between 2 and 255 characters")
    private String author;
    @NotNull(message = "Date of publication cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date published;

    public Book() {
    }

    public Book(long id, String title, String author, Date published) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.published = published;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", published=" + published +
                '}';
    }
}
