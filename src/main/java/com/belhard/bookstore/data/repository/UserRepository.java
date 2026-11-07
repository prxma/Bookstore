package com.belhard.bookstore.data.repository;

import com.belhard.bookstore.data.entity.RoleEnum;
import com.belhard.bookstore.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
    List<User> findBySurname(String surname);


    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);

    long countByRoles_Name(RoleEnum name);
    long countByCreatedAtAfter(LocalDateTime time);
}
