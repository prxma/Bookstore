package com.belhard.bookstore.service;

import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.entity.BookGenre;
import com.belhard.bookstore.data.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.persistence.*;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
    public List<Book> search(String q) {
        if (q == null || q.isBlank()) return bookRepository.findAll();
        return bookRepository.search(q.trim());
    }

    public List<Book> recommendFor(Book book) {
        Set<BookGenre> mine = book.getGenres();
        if (mine == null || mine.isEmpty()) return List.of();
        return bookRepository.findDistinctByGenresInAndIdNot(mine, book.getId()).stream()
                .sorted(Comparator.comparingLong(
                        (Book b) -> b.getGenres().stream().filter(mine::contains).count()).reversed())
                .limit(4)
                .toList();
    }

}
