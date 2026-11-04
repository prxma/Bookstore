package com.belhard.bookstore.service;

import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    public final UserRepository userRepository;

    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> findBySurname(String surname) {
        return userRepository.findBySurname(surname);
    }

}
