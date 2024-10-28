package com.yuzarsif.qnaservice.dto;

import com.yuzarsif.qnaservice.model.Question;

import java.time.LocalDateTime;
import java.util.List;

public record QuestionDto(
    String id,
    String customerId,
    String productId,
    String question,
    AnswerDto answers,
    LocalDateTime createdAt
) {

    public static QuestionDto convert(Question from) {
        return new QuestionDto(
                from.getId(),
                from.getCustomerId(),
                from.getProductId().toString(),
                from.getQuestion(),
                AnswerDto.convert(from.getAnswer()),
                from.getCreatedAt());
    }
}
