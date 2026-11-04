package com.belhard.bookstore.data.repository;

import com.belhard.bookstore.data.entity.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<BookGenre, Long> {
}
