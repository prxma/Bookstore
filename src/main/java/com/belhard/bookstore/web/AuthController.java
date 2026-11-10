package com.belhard.bookstore.web;

import com.belhard.bookstore.service.EmailIsAlreadyUsedException;
import com.belhard.bookstore.service.RegistrationService;
import com.belhard.bookstore.web.dto.RegistrationForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("form", new RegistrationForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid
                           @ModelAttribute("form")
                           RegistrationForm form,
                           BindingResult result) {

        if (!result.hasFieldErrors("password") && !result.hasFieldErrors("repeatPassword")) {
            if (!form.getPassword().equals(form.getConfirmPassword())) {
                result.rejectValue("repeatPassword", "mismatch", "Passwords are not similar");
            } else if (form.getPassword().getBytes(StandardCharsets.UTF_8).length > 80) {
                result.rejectValue("password", "too_long", "Password is too long");
            }
        }

        if (result.hasErrors()) {
            return "register";
        }

        try {
            registrationService.register(form);
        } catch (EmailIsAlreadyUsedException e) {
            result.rejectValue("email", "similar", "This email is already exist");
            return "register";
        }

        return "redirect:/login?registered";
    }
}
