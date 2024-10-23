package com.yuzarsif.cartservice.client;

public record StockResponse(
        Long productId,
        Integer quantity
) {
}
