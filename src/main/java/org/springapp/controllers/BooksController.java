package org.springapp.controllers;

import jakarta.validation.Valid;
import org.springapp.constants.BookConstants;
import org.springapp.models.Book;
import org.springapp.models.Human;
import org.springapp.service.BookService;
import org.springapp.service.HumanService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String indexPage(Model model) {
        model.addAttribute("books", bookService.getList());
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
        model.addAttribute("book", bookService.getBook(id));
        if (bookService.getOwner(id) != null) {
            model.addAttribute("human", bookService.getOwner(id));
        } else {
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
}
