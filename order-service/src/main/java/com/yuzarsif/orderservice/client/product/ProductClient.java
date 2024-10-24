package com.yuzarsif.orderservice.client.product;

import com.yuzarsif.orderservice.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductClient {

    private final RestTemplate restTemplate;

    public Boolean existById(Long id) {
        try {
            return restTemplate.getForObject("http://PRODUCT-SERVICE/api/v1/products/exists/{id}", Boolean.class, id);
        } catch (HttpClientErrorException ex) {
            throw new ClientException("Product client error occurred while checking product exist. Url: http://PRODUCT-SERVICE/api/v1/products/exists/" + id);
        }
    }

    public List<ProductResponse> getProductById(List<Long> ids) {
        String idsAsString = String.join(",", ids.stream().map(String::valueOf).toArray(String[]::new));

        String url = "http://PRODUCT-SERVICE/api/v1/products/list/" + idsAsString;

        try {
            return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductResponse>>() {}).getBody();
        } catch (HttpClientErrorException ex) {
            throw new ClientException("Product client error occurred while fetching products. Url: http://PRODUCT-SERVICE/api/v1/products/list/" + idsAsString);
        }
    }
}
