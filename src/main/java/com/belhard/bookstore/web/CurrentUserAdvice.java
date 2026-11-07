package com.belhard.bookstore.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class CurrentUserAdvice {
    @ModelAttribute("currentEmail")
    public String currentEmail(Principal principal) {
        return principal == null ? null : principal.getName();
    }
}
