package com.belhard.bookstore.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "genres")
@Data
public class BookGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
}
