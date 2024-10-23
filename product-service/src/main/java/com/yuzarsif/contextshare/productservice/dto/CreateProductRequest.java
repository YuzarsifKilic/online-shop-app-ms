package com.yuzarsif.contextshare.productservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateProductRequest(

        @NotNull(message = "Name cannot be null")
        String name,
        @NotNull(message = "Description cannot be null")
        String description,
        @NotNull @Min(value = 0, message = "Price cannot be negative")
        Double price,
        @NotNull(message = "MainImageUrl cannot be null")
        String mainImageUrl,
        @NotNull(message = "Category id cannot be null")
        Integer categoryId,
        @NotNull(message = "Company id cannot be null")
        String companyId
) {
}
