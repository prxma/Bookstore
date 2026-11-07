package com.belhard.bookstore.service;

import com.belhard.bookstore.data.entity.Role;
import com.belhard.bookstore.data.entity.RoleEnum;
import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.repository.BookRepository;
import com.belhard.bookstore.data.repository.RoleRepository;
import com.belhard.bookstore.data.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final int PAGE_SIZE = 20;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BookRepository bookRepository;

    @Data
    @AllArgsConstructor
    public static class Stats {
        private long users;
        private long usersPerWeek;
        private long admins;
        private long books;
    }

    @Transactional(readOnly = true)
    public Stats stats() {
        return new Stats(
                userRepository.count(),
                userRepository.countByCreatedAtAfter(LocalDateTime.now().minusDays(7)),
                userRepository.countByRoles_Name(RoleEnum.ADMIN),
                bookRepository.count());
    }

    @Transactional(readOnly = true)
    public Page<User> findUsers(int page) {
        return userRepository.findAll(PageRequest.of(Math.max(page, 0), PAGE_SIZE,
                Sort.by(Sort.Order.desc("userId"))));
    }

    @Transactional(readOnly = true)
    public List<User> latestUsers(int count) {
        return userRepository.findAll(PageRequest.of(0, count,
                Sort.by(Sort.Order.desc("createdAt"), Sort.Order.desc("userId")))).getContent();
    }

    @Transactional
    public void giveAdminPriority(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Nothing was found"));
        if (user.isAdmin()) {
            return;
        }
        Role adminRole = roleRepository.findByName(RoleEnum.ADMIN)
                .orElseThrow(() -> new IllegalArgumentException("Admin was not found"));
        user.getRoles().add(adminRole);
    }
    @Transactional
    public void removeAdminPriority(Long userId, String selfEmail) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Nothing was found"));
        if (user.getEmail().equalsIgnoreCase(selfEmail)) {
            throw new IllegalStateException("You cannot remove admin priority from yourself");
        }
        if (!user.isAdmin()) {
            return;
        }
        if (userRepository.countByRoles_Name(RoleEnum.ADMIN) <= 1) {
            throw new IllegalStateException("This is the last admin");
        }

        user.getRoles().removeIf(r -> r.getName() == RoleEnum.ADMIN);
        if(user.getRoles().isEmpty()) {
            Role userRole = roleRepository.findByName(RoleEnum.USER)
                    .orElseThrow(() -> new IllegalStateException("No users was found"));
            user.getRoles().add(userRole);
        }

    }

}
