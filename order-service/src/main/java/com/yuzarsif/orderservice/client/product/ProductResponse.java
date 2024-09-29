package com.yuzarsif.orderservice.client.product;


import java.util.List;

public record ProductResponse(
    Long id,
    String name,
    String description,
    Double price,
    String mainImageUrl,
    CategoryResponse category,
    List<PhotoResponse> photos
) {
}
