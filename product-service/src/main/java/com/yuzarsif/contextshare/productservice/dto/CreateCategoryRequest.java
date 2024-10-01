package com.yuzarsif.contextshare.productservice.dto;

import jakarta.validation.constraints.NotNull;

public record CreateCategoryRequest(
        @NotNull(message = "Name cannot be null")
        String name
) {
}
