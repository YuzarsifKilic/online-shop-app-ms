package com.yuzarsif.contextshare.productservice.dto;

public record ProductList(
        Long id,
        String name,
        Double price,
        String mainImageUrl
) {
}
