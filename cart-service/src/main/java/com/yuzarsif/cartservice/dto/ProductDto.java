package com.yuzarsif.cartservice.dto;

public record ProductDto(
        Long productId,
        String name,
        Double price,
        String mainImageUrl,
        Integer quantity,
        Integer stock
) {
}
