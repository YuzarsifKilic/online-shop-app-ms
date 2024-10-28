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
@Document("answer")
public class Answer {

    @Id
    private String id;
    private String sellerId;
    private String reply;
    private LocalDateTime createdAt;
}
