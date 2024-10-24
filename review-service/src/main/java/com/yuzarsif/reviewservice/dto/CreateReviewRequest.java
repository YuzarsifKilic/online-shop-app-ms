package com.yuzarsif.reviewservice.dto;

public record CreateReviewRequest(
        String customerId,
        Long productId,
        String comment,
        Integer rating
) {
}
