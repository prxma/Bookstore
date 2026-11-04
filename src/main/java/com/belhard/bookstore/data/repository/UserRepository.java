package com.belhard.bookstore.data.repository;

import com.belhard.bookstore.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
    List<User> findBySurname(String surname);
}
