package com.yuzarsif.contextshare.productservice.dto;

import com.yuzarsif.contextshare.productservice.model.Category;

public record CategoryDto(
    Integer id,
    String name
) {

    public static CategoryDto convert(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
