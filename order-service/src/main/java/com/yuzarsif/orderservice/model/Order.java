package com.yuzarsif.orderservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("orders")
public class Order {

    @Id
    private String id;
    private String userId;
    private Set<Product> products;
    private OrderStatus orderStatus;
    private Long addressId;
}
