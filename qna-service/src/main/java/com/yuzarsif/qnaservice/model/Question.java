package com.yuzarsif.qnaservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("questions")
public class Question {

    @Id
    private String id;
    private String customerId;
    private Long productId;
    private String question;
    private LocalDateTime createdAt;
    private Answer answer;
}
