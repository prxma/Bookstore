package com.belhard.bookstore.web;

import com.belhard.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/{name}")
    public String usersName(@PathVariable String name, Model model) {
        model.addAttribute("users", userService.findByName(name));
        return "users";
    }

    @GetMapping("/users/{surname}")
    public String usersSurname(@PathVariable String surname, Model model) {
        model.addAttribute("users", userService.findBySurname(surname));
        return "users";
    }
}
