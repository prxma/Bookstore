package com.belhard.bookstore.service;

import com.belhard.bookstore.data.entity.Book;
import com.belhard.bookstore.data.entity.Purchase;
import com.belhard.bookstore.data.entity.User;
import com.belhard.bookstore.data.repository.BookRepository;
import com.belhard.bookstore.data.repository.PurchaseRepository;
import com.belhard.bookstore.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Transactional
    public Purchase buy(String email, Long bookId) {
        return buyMany(email, List.of(bookId)).get(0);
    }

    @Transactional
    public List<Purchase> buyMany(String email, List<Long> bookIds) {
        if(bookIds == null || bookIds.isEmpty()) {
            throw new IllegalArgumentException("Pick up one to buy");
        }

        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalStateException("Nothing was found"));

        List<Purchase> result = new ArrayList<>();

        for(Long bookId : new LinkedHashSet<>(bookIds)) {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new IllegalStateException("This books is not on sale"));

            Purchase purchase = new Purchase();
            purchase.setUser(user);
            purchase.setBook(book);
            purchase.setPrice(book.getPrice());
            purchase.setPurchasedAt(LocalDateTime.now());

            result.add(purchaseRepository.save(purchase));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<Purchase> findByEmail(String email) {
        return purchaseRepository.findByUserEmailIgnoreCaseOrderByPurchasedAtDesc(email);
    }
}
