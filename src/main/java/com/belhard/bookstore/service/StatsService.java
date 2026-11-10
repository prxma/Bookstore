package com.belhard.bookstore.service;


import com.belhard.bookstore.data.entity.Purchase;
import com.belhard.bookstore.data.repository.PurchaseRepository;
import com.belhard.bookstore.service.dto.LabelSales;
import com.belhard.bookstore.service.dto.SalePoint;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatsService {

    private static final int DAYS = 30;
    private static final int PAGE_SIZE = 20;

    private final PurchaseRepository purchaseRepository;

    @Value
    public static class Bar {
        String label;
        long count;
        BigDecimal revenue;
        int pct;
    }

    @Value
    public static class Day {
        LocalDate date;
        long count;
        BigDecimal revenue;
        int pct;
    }

    @Value
    public static class Overview {
        long purchases;
        long buyers;
        BigDecimal revenue;
        BigDecimal revenue30;
        BigDecimal average;
    }

    @Value
    public static class Report {
        Overview overview;
        List<Day> days;
        List<Bar> topBooks;
        List<Bar> genres;
        List<Bar> buyers;
        List<Purchase> latest;
    }

    @Transactional(readOnly = true)
    public Report report() {

        LocalDate today = LocalDate.now();
        LocalDate from = today.minusDays(DAYS - 1L);

        Map<LocalDate, BigDecimal> revenueByDay = new HashMap<>();
        Map<LocalDate, Long> countByDay = new HashMap<>();

        for (SalePoint p : purchaseRepository.salePointsSince(from.atStartOfDay())) {
            LocalDate d = p.at().toLocalDate();
            revenueByDay.merge(d, p.price(), BigDecimal::add);
            countByDay.merge(d, 1L, Long::sum);
        }

        BigDecimal maxDay = revenueByDay.values().stream()
                .max(Comparator.naturalOrder()).orElse(BigDecimal.ZERO);
        BigDecimal revenue30 = revenueByDay.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Day> days = from.datesUntil(today.plusDays(1))
                .map(d -> {
                    BigDecimal rev = revenueByDay.getOrDefault(d, BigDecimal.ZERO);
                    return new Day(d, countByDay.getOrDefault(d, 0L), rev, percent(rev, maxDay));
                }).toList();

        long purchases = purchaseRepository.count();
        BigDecimal revenue = purchaseRepository.totalRevenue();
        if (revenue == null) {
            revenue = BigDecimal.ZERO;
        }
        BigDecimal average = purchases == 0 ? BigDecimal.ZERO : revenue.divide(BigDecimal.valueOf(purchases), 2, RoundingMode.HALF_UP);
        Overview overview = new Overview(purchases, purchaseRepository.countBuyers(),
                revenue, revenue30, average);

        return new Report(overview, days,
                toBars(purchaseRepository.topBooks(PageRequest.of(0, 10))),
                toBars(purchaseRepository.salesByGenre()),
                toBars(purchaseRepository.topBuyers(PageRequest.of(0, 5))),
                purchaseRepository.findTop10ByOrderByPurchasedAtDesc());
    }
    @Transactional(readOnly = true)
    public Page<Purchase> purchasesPage(int page) {
        return purchaseRepository.findAll(PageRequest.of(Math.max(page, 0), PAGE_SIZE,
                Sort.by(Sort.Order.desc("purchasedAt"), Sort.Order.desc("id"))));
    }

    private static int percent(BigDecimal value, BigDecimal max) {
        if (max.signum() == 0 || value.signum() == 0) {
            return 0;
        }
        int pct = value.multiply(BigDecimal.valueOf(100)).divide(max, 0, RoundingMode.HALF_UP).intValue();
        return Math.max(pct, 3);
    }

    private static List<Bar> toBars(List<LabelSales> rows) {
        long max = rows.stream().mapToLong(LabelSales::count).max().orElse(0);
        return rows.stream()
                .map(r -> new Bar(
                    r.label(),
                    r.count(),
                    r.revenue() == null ? BigDecimal.ZERO : r.revenue(),
                    max == 0 ? 0 : (int) Math.max(3, Math.round(r.count() * 100.0 / max)))).toList();
    }
}
