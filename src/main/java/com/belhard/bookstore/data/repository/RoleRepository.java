package com.belhard.bookstore.data.repository;

import com.belhard.bookstore.data.entity.Role;
import com.belhard.bookstore.data.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
