package com.yuzarsif.orderservice.dto;

import com.yuzarsif.orderservice.model.Order;
import com.yuzarsif.orderservice.model.Products;

import java.util.Set;

public record OrderDto(
    String id,
    Set<Products> products
) {

    public static OrderDto convert(Order from) {
        return new OrderDto(
                from.getId(),
                from.getProducts());
    }
}
