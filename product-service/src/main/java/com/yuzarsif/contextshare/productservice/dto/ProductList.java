package com.yuzarsif.contextshare.productservice.dto;

import com.yuzarsif.contextshare.productservice.model.Product;

import java.util.List;
import java.util.Set;

public record ProductList(
        Long id,
        String name,
        Double price,
        String mainImageUrl
) {

    public static ProductList convert(Product from) {
        return new ProductList(
                from.getId(),
                from.getName(),
                from.getPrice(),
                from.getMainImageUrl());
    }

    public static List<ProductList> convert(Set<Product> from) {
        return from.stream().map(ProductList::convert).toList();
    }
}
