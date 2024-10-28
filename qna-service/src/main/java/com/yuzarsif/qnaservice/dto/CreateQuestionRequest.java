package com.yuzarsif.qnaservice.dto;

public record CreateQuestionRequest(
    String customerId,
    Long productId,
    String question
) {
}
