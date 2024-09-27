package com.yuzarsif.contextshare.productservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreatePhotoRequest(
        @NotNull
        Long productId,
        @NotEmpty
        List<String> urls
) {
}
