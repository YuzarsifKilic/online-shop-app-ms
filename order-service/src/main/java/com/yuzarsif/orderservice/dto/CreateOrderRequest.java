package com.yuzarsif.orderservice.dto;

import com.yuzarsif.orderservice.model.Products;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(
        @NotNull
        String userId,
        @NotEmpty
        List<ProductRequest> products
) {
}
