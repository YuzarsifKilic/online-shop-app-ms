package com.yuzarsif.stockservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateStockRequest(
        @NotNull(message = "Product id cannot be null")
        Long productId,
        @Min(value = 0, message = "Quantity cannot be negative when creating stock")
        Integer quantity
) {
}
