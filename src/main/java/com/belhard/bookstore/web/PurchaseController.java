package com.belhard.bookstore.web;

import com.belhard.bookstore.data.entity.Purchase;
import com.belhard.bookstore.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/books/{id}/buy")
    public String buy(@PathVariable Long id,
                      Principal principal,
                      RedirectAttributes redirectAttributes) {
        try {
            purchaseService.buy(principal.getName(), id);
            redirectAttributes.addFlashAttribute("message", "Thanks for buying");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/books/" + id;
    }

    @PostMapping("/purchases/buy-many")
    public String buyMany(@RequestParam(required = false) List<Long>bookIds,
                          Principal principal, RedirectAttributes redirectAttributes) {
        try {
            List<Purchase> bought = purchaseService.buyMany(principal.getName(), bookIds);
            redirectAttributes.addFlashAttribute("message", "Order created: " + bought.size() + " books");
            return "redirect:/purchases";
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/books";
        }
    }

    @GetMapping("/purchases")
    public String myPurchases(Principal principal, Model model) {
        List<Purchase> purchases = purchaseService.findByEmail(principal.getName());
        BigDecimal total = purchases.stream()
                .map(Purchase::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("purchases", purchases);
        model.addAttribute("total", total);
        return "purchases";
    }
}
