package com.yuzarsif.orderservice.dto;

import com.yuzarsif.orderservice.model.Order;
import com.yuzarsif.orderservice.model.OrderStatus;
import com.yuzarsif.orderservice.model.Product;


import java.util.Set;

public record OrderDto(
    String id,
    String userId,
    Set<Product> products,
    OrderStatus orderStatus
) {

    public static OrderDto convert(Order from) {
        return new OrderDto(
                from.getId(),
                from.getUserId(),
                from.getProducts(),
                from.getOrderStatus());
    }
}
