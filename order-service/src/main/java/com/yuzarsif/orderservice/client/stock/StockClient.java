package com.yuzarsif.orderservice.client.stock;

import com.yuzarsif.orderservice.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Update;
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

    public void updateStock(Long productId, UpdateStockRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);

        HttpEntity<UpdateStockRequest> requestEntity = new HttpEntity<>(request, headers);

        try {
            restTemplate.exchange(
                    "http://STOCK-SERVICE/api/v1/stocks/" + productId,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new ClientException(String.format(
                    "Stock client error occurred while updating stock for product. " +
                            "Url: http://STOCK-SERVICE/api/v1/stocks/%s " +
                            "Request details: %s",
                    productId, request));
        }
    }
}
