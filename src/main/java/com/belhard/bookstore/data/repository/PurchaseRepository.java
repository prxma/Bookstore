package com.belhard.bookstore.data.repository;

import com.belhard.bookstore.data.entity.Purchase;
import com.belhard.bookstore.service.dto.LabelSales;
import com.belhard.bookstore.service.dto.SalePoint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUserEmailIgnoreCaseOrderByPurchasedAtDesc(String email);

    List<Purchase> findTop10ByOrderByPurchasedAtDesc();

    @Query("select sum(p.price) from Purchase p")
    BigDecimal totalRevenue();

    @Query("select count(distinct p.user.id) from Purchase p")
    long countBuyers();

    @Query("select new com.belhard.bookstore.service.dto.SalePoint(p.purchasedAt, p.price) " +
            "from Purchase p where p.purchasedAt >= :from")
    List<SalePoint> salePointsSince(@Param("from") LocalDateTime from);

    @Query("select new com.belhard.bookstore.service.dto.LabelSales(b.title, count(p), sum(p.price)) " +
            "from Purchase p join p.book b group by b.id, b.title order by count(p) desc, sum(p.price) desc")
    List<LabelSales> topBooks(Pageable pageable);

    @Query("select new com.belhard.bookstore.service.dto.LabelSales(g.name, count(p), sum(p.price)) " +
            "from Purchase p join p.book b join b.genres g group by g.name order by count(p) desc")
    List<LabelSales> salesByGenre();

    @Query("select new com.belhard.bookstore.service.dto.LabelSales(u.email, count(p), sum(p.price)) " +
                  "from Purchase p join p.user u group by u.email order by sum(p.price) desc")
    List<LabelSales> topBuyers(Pageable pageable);
}
