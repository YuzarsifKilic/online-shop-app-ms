package com.yuzarsif.stockservice.dto;

import com.yuzarsif.stockservice.model.Stock;

public record StockDto(
        Long productId,
        Integer quantity
) {

    public static StockDto convert(Stock from) {
        return new StockDto(
                from.getProductId(),
                from.getQuantity());
    }
}
