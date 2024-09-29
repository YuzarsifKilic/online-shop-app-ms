package com.yuzarsif.orderservice.client.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;

    public Boolean existById(Long id) {
        return restTemplate.getForObject("http://PRODUCT_SERVICE/products/{id}/exists", Boolean.class, id);
    }

    public ProductResponse getProductById(Long productId) {
        return restTemplate.getForObject("http://PRODUCT_SERVICE/products/{id}", ProductResponse.class, productId);
    }
}
