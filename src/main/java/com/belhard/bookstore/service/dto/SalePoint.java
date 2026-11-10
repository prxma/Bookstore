package com.belhard.bookstore.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SalePoint(LocalDateTime at, BigDecimal price) {
}
