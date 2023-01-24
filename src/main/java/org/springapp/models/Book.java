package org.springapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Title must contain only letters, numbers and spaces")
    @Column(name = "title")
    private String title;
    @NotEmpty(message = "Author cannot be empty")
    @Size(min = 2, max = 255, message = "Author must be between 2 and 255 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Author must contain only letters and spaces")
    @Column(name = "author")
    private String author;
    @NotNull(message = "Date of publication cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Date of publication cannot be in the future")
    @Column(name = "published")
    private Date published;

    @Column(name = "date_requested")
    private Date dateRequested;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Human owner;

    @Transient
    private boolean expired;

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

    public Human getOwner() {
        return owner;
    }

    public void setOwner(Human owner) {
        this.owner = owner;
    }

    public Date getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(Date dateRequested) {
        this.dateRequested = dateRequested;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired() {
        if (dateRequested != null) {
            long diff = new Date().getTime() - dateRequested.getTime();
            this.expired = diff > 10 * 24 * 60 * 60 * 1000;
        }
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
