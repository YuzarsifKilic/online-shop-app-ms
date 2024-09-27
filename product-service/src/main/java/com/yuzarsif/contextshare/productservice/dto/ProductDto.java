package com.yuzarsif.contextshare.productservice.dto;

import com.yuzarsif.contextshare.productservice.model.Product;

import java.util.List;
import java.util.Set;

public record ProductDto(
    Long id,
    String name,
    String description,
    Double price,
    String mainImageUrl,
    CategoryDto category,
    List<PhotoDto> photos
) {

    public static ProductDto convert(Product from) {
        return new ProductDto(
                from.getId(),
                from.getName(),
                from.getDescription(),
                from.getPrice(),
                from.getMainImageUrl(),
                CategoryDto.convert(from.getCategory()),
                PhotoDto.convert(from.getPhotos()));
    }

    public static List<ProductDto> convert(Set<Product> products) {
        return products
                .stream()
                .map(ProductDto::convert)
                .toList();
    }
}
