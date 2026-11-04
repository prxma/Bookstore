package com.belhard.bookstore.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "covers")
@Data
public class Cover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private CoverEnum name;

    @Override
    public String toString() {
        return String.valueOf(name);
    }
}