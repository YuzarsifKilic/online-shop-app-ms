package com.yuzarsif.orderservice.model;

import com.yuzarsif.orderservice.client.product.ProductResponse;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private Long productId;
    private Integer quantity;
}
