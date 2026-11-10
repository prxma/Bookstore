package com.belhard.bookstore.service.dto;

import java.math.BigDecimal;

public record LabelSales(String label, Long count, BigDecimal revenue) {
}
