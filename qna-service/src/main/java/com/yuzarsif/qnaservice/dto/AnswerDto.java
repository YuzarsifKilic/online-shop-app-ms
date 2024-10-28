package com.yuzarsif.qnaservice.dto;

import com.yuzarsif.qnaservice.model.Answer;

import java.time.LocalDateTime;

public record AnswerDto(
        String id,
        String sellerId,
        String reply,
        LocalDateTime createdAt
) {

    public static AnswerDto convert(Answer from) {
        return new AnswerDto(
                from.getId(),
                from.getSellerId(),
                from.getReply(),
                from.getCreatedAt());
    }
}
