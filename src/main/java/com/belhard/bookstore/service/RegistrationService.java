package com.belhard.bookstore.service;

import com.belhard.bookstore.data.entity.Role;
import com.belhard.bookstore.data.entity.RoleEnum;
import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.repository.RoleRepository;
import com.belhard.bookstore.data.repository.UserRepository;
import com.belhard.bookstore.web.dto.RegistrationForm;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(RegistrationForm form) {
        String email = form.getEmail().trim().toLowerCase(Locale.ROOT);

        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new EmailIsAlreadyUsedException(email);
        }

        Role userRole = roleRepository.findByName(RoleEnum.USER)
                .orElseThrow(() -> new IllegalStateException("Role user is not found. Please rebuild ur databases"));

        User user = new User();
        user.setName(form.getName().trim());
        user.setSurname(form.getSurname().trim());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setRoles(new HashSet<>(Set.of(userRole)));

        try {
            return userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException e) {
            throw new EmailIsAlreadyUsedException(email);
        }
    }
}
