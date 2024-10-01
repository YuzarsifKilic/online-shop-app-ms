package com.yuzarsif.orderservice.client.stock;

public record StockResponse(
        Long productId,
        Integer quantity
) {
}
