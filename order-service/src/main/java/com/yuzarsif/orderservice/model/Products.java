package com.yuzarsif.orderservice.model;

import com.yuzarsif.orderservice.client.product.ProductResponse;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "products")
public class Products {

    @Id
    private String id;
    private ProductResponse product;
    private Integer quantity;
}
