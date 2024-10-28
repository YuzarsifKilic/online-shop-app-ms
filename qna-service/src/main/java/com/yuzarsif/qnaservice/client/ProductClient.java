package com.yuzarsif.qnaservice.client;

import com.yuzarsif.qnaservice.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;

    public Boolean checkProductExists(Long productId) {
        try {
            return restTemplate.getForObject("http://PRODUCT-SERVICE/api/v1/products/exists/{id}", Boolean.class, productId);
        } catch (HttpClientErrorException ex) {
            throw new ClientException(String.format(
                    "An error occurred while trying to access product information. " +
                            "Url: http://PRODUCT-SERVICE/api/v1/products/exists/%s", productId));
        }
    }
}
