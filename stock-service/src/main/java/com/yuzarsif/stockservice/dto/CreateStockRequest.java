package com.yuzarsif.stockservice.dto;

import jakarta.validation.constraints.Min;

public record CreateStockRequest(
    Long productId,
    @Min(value = 0, message = "Quantity cannot be negative when creating stock")
    Integer quantity
) {
}
