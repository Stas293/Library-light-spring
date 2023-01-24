package org.springapp.controllers;

import jakarta.validation.Valid;
import org.springapp.constants.BookConstants;
import org.springapp.models.Book;
import org.springapp.models.Human;
import org.springapp.service.BookService;
import org.springapp.service.HumanService;
import org.springapp.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookService bookService;
    private final HumanService humanService;

    @Autowired
    public BooksController(BookService bookService, HumanService humanService) {
        this.bookService = bookService;
        this.humanService = humanService;
    }

    @GetMapping()
    public String indexPage(
            @RequestParam(value = "pageNum", required = false, defaultValue = "0")int pageNum,
            @RequestParam(value = "books_per_page", required = false, defaultValue = "5")int booksPerPage,
            @RequestParam(value = "sort", required = false, defaultValue = "title")String sort,
            Model model) {
        Page<Book> books = bookService.getPage(pageNum, booksPerPage, sort);
        model.addAttribute("books", books);
        model.addAttribute("pageSequence", Utils.getSequencePages(books.getTotalPages()));
        model.addAttribute("sorts", BookConstants.SORTS);
        return BookConstants.BOOKS_INDEX;
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return BookConstants.BOOKS_NEW;
        }
        bookService.save(book);
        return BookConstants.REDIRECT_BOOKS;
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book person) {
        return BookConstants.BOOKS_NEW;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Human person) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        if (book.getOwner() == null) {
            model.addAttribute("humans", humanService.getPeople());
        }
        return BookConstants.BOOKS_BOOK;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return BookConstants.REDIRECT_BOOKS_ID + id;
    }

    @PatchMapping("/{id}/take")
    public String take(@PathVariable("id") int id, @ModelAttribute("person") Human person) {
        bookService.take(id, person);
        return BookConstants.REDIRECT_BOOKS_ID + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return BookConstants.REDIRECT_BOOKS;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.getBook(id));
        return BookConstants.BOOKS_EDIT;
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return BookConstants.BOOKS_EDIT;
        }

        bookService.update(book);
        return BookConstants.REDIRECT_BOOKS;
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(value = "search", required = false, defaultValue = "") String query,
            @RequestParam(value = "pageNum", required = false, defaultValue = "0")int pageNum,
            @RequestParam(value = "books_per_page", required = false, defaultValue = "5")int booksPerPage,
            @RequestParam(value = "sort", required = false, defaultValue = "title")String sort,
            Model model) {
        Page<Book> books = bookService.search(query, pageNum, booksPerPage, sort);
        model.addAttribute("books", books);
        model.addAttribute("pageSequence", Utils.getSequencePages(books.getTotalPages()));
        model.addAttribute("sorts", BookConstants.SORTS);
        return BookConstants.BOOKS_SEARCH;
    }
}
