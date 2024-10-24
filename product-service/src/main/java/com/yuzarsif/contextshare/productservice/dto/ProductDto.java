package com.yuzarsif.contextshare.productservice.dto;

import com.yuzarsif.contextshare.productservice.client.CompanyResponse;
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
    List<PhotoDto> photos,
    CompanyResponse company
) {

    public static ProductDto convert(Product from, CompanyResponse company) {
        return new ProductDto(
                from.getId(),
                from.getName(),
                from.getDescription(),
                from.getPrice(),
                from.getMainImageUrl(),
                CategoryDto.convert(from.getCategory()),
                from.getPhotos() == null ? null : PhotoDto.convert(from.getPhotos()),
                company);
    }

}
