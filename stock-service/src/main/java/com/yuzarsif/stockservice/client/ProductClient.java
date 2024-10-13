package com.yuzarsif.stockservice.client;

import com.yuzarsif.stockservice.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;

    public Boolean existById(Long id) {
        try {
            restTemplate.getForObject("http://PRODUCT-SERVICE/api/v1/products/exists/{id}", Boolean.class, id);
        } catch (HttpClientErrorException ex) {
            throw new ClientException("Product client error occurred while checking product exist. Url: http://PRODUCT-SERVICE/api/v1/products/exists/" + id);
        }
        return restTemplate.getForObject("http://PRODUCT-SERVICE/api/v1/products/exists/{id}", Boolean.class, id);
    }

}
