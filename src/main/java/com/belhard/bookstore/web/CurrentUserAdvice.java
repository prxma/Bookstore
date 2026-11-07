package com.belhard.bookstore.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.core.Authentication;

import java.security.Principal;

@ControllerAdvice
public class CurrentUserAdvice {

    @ModelAttribute("currentEmail")
    public String currentEmail(Principal principal) {
        return principal == null ? null : principal.getName();
    }

    @ModelAttribute("isAdmin")
    public boolean isAdmin(Authentication authentication) {
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
