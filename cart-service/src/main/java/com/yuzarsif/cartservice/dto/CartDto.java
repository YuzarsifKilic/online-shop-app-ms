package com.yuzarsif.cartservice.dto;

import java.util.List;

public record CartDto(
    String customerId,
    List<ProductDto> products
) {
}
