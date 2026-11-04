package com.belhard.bookstore.web;

import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/books")
    public String books(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("books", bookService.search(q));
        model.addAttribute("q", q);
        return "books";
    }

    @GetMapping("/books/{id}")
    public String book(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("similar", bookService.recommendFor(book));
        return "book";
    }
}
