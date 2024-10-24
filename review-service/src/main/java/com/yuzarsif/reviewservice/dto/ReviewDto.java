package com.yuzarsif.reviewservice.dto;

import com.yuzarsif.reviewservice.model.Review;

public record ReviewDto(
    String id,
    String customerId,
    Long productId,
    String comment,
    Integer rating
) {

    public static ReviewDto convert(Review from) {
        return new ReviewDto(
                from.getId(),
                from.getCustomerId(),
                from.getProductId(),
                from.getComment(),
                from.getRating());
    }
}
