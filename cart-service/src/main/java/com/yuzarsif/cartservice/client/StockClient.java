package com.yuzarsif.cartservice.client;

import com.yuzarsif.cartservice.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class StockClient {

    private final RestTemplate restTemplate;

    public StockResponse findStockByProductId(Long productId) {
        try {
            return restTemplate.getForObject("http://STOCK-SERVICE/api/v1/stocks/product/{productId}", StockResponse.class, productId);
        } catch (HttpClientErrorException ex) {
            throw new ClientException("Stock Client error occurred while fetching stock. Url: http://STOCK-SERVICE/api/v1/stocks/product/" + productId);
        }
    }
}
