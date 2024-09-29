package com.yuzarsif.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        @NotNull
        Long productId,
        @NotNull @Min(value = 1, message = "Quantity should be greater than or equal to 1")
        Integer quantity
) {
}
