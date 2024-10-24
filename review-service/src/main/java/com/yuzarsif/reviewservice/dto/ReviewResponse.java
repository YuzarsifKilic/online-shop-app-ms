package com.yuzarsif.reviewservice.dto;

public record ReviewResponse(
    String id,
    String customerId,
    String customerName,
    Long productId,
    String comment,
    Integer rating
) {
}
