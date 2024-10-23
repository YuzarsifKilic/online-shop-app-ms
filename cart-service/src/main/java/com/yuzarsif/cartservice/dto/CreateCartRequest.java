package com.yuzarsif.cartservice.dto;

public record CreateCartRequest(
        String customerId,
        Long productId,
        Integer quantity
) {
}
