package com.yuzarsif.reviewservice.client;

import com.yuzarsif.reviewservice.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OrderClient {

    private final RestTemplate restTemplate;

    public Boolean checkOrderExists(String customerId, Long productId) {
        try {
            restTemplate.getForObject("http://ORDER-SERVICE/api/v1/orders/check-order-exists?userId={customerId}&productId={productId}", Boolean.class, customerId, productId);
        } catch (HttpClientErrorException ex) {
            throw new ClientException("Order client error occurred while checking order exist. Url: http://ORDER-SERVICE/api/v1/orders/check-order-exists?userId=" + customerId + "&productId=" + productId);
        }
        return restTemplate.getForObject("http://ORDER-SERVICE/api/v1/orders/check-order-exists?userId={customerId}&productId={productId}", Boolean.class, customerId, productId);}
}
