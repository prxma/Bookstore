package com.belhard.bookstore.web;

import com.belhard.bookstore.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.belhard.bookstore.service.StatsService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final StatsService statsService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("stats", adminService.stats());
        model.addAttribute("latest", adminService.latestUsers(5));
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String users(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("usersPage", adminService.findUsers(page));
        return "admin/users";
    }

    @PostMapping("/users/{id}/make-admin")
    public String makeAdmin(@PathVariable Long id,
                            @RequestParam(defaultValue = "0") int page,
                            RedirectAttributes redirectAttributes) {
        try {
            adminService.giveAdminPriority(id);
            redirectAttributes.addFlashAttribute("message", "New admin is created");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users?page=" + page;
    }

    @PostMapping("/users/{id}/remove-admin")
    public String removeAdmin(@PathVariable Long id,
                              @RequestParam(defaultValue = "0") int page,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
        try {
            adminService.removeAdminPriority(id, principal.getName());
            redirectAttributes.addFlashAttribute("message", "Admin is deleted");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users?page=" + page;
    }

    @GetMapping("/purchases")
    public String purchases(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("purchasesPage", statsService.purchasesPage(page));
        return "admin/purchases";
    }

    @GetMapping("/stats")
    public String stats(Model model) {
        model.addAttribute("r", statsService.report());
        return "admin/stats";
    }
}
