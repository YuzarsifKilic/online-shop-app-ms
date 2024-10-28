package com.yuzarsif.orderservice.dto;

import com.yuzarsif.orderservice.client.product.ProductResponse;
import com.yuzarsif.orderservice.client.user.AddressResponse;

import java.util.List;

public record OrderResponse(
        String id,
        String customerId,
        List<ProductResponse> products,
        AddressResponse address
) {
}
