package com.yuzarsif.stockservice.dto;

import com.yuzarsif.stockservice.model.Stock;

public record StockDto(
        Long id,
        Long productId,
        Integer quantity
) {

    public static StockDto convert(Stock from) {
        return new StockDto(
                from.getId(),
                from.getProductId(),
                from.getQuantity());
    }
}
