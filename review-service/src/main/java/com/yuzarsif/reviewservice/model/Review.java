package com.yuzarsif.reviewservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("reviews")
public class Review {

    @Id
    public String id;
    public String customerId;
    public Long productId;
    public String comment;
    public Integer rating;
}
