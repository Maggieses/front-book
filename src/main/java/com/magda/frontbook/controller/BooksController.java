package com.magda.frontbook.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.magda.frontbook.client.BookClient;
import com.magda.frontbook.model.Book;
import com.magda.frontbook.model.BookView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping
@Controller
public class BooksController {

    @Autowired
    BookClient bookClient;

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("books", bookClient.findAll());
        return "index";
    }

    @GetMapping("/books/{id}")
    public String details(@PathVariable("id") final long id, Model model) {
        final BookView bookView = bookClient.getBook(id);
        model.addAttribute("book", bookView);
        return "book";
    }

    @GetMapping("/books/newbook")
    public String newBook(BookView bookView) {
        return "add_book";
    }

    @DeleteMapping("/books/{id}")
    public String deleteBook(@PathVariable("id") final long id, Model model) {
        bookClient.deleteBook(id);
        model.addAttribute("books", bookClient.findAll());
        return "index";
    }

    @PostMapping("/books")
    public String addBook(@ModelAttribute BookView bookView, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add_book";
        }

        bookClient.addBook(bookView);
        model.addAttribute("books", bookClient.findAll());
        return "index";
    }
}
