package com.yuzarsif.stockservice.service;

import com.yuzarsif.stockservice.client.ProductClient;
import com.yuzarsif.stockservice.dto.CreateStockRequest;
import com.yuzarsif.stockservice.dto.UpdateStockRequest;
import com.yuzarsif.stockservice.exception.EntityNotFoundException;
import com.yuzarsif.stockservice.model.Stock;
import com.yuzarsif.stockservice.repository.StockRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final ProductClient productClient;

    public void createStock(CreateStockRequest request) {

        Boolean productExists = productClient.existById(request.productId());

        if (!productExists) {
            throw new EntityNotFoundException("Product not found with id: " + request.productId());
        }

        Stock stock = Stock
                .builder()
                .productId(request.productId())
                .quantity(request.quantity())
                .build();

        stockRepository.save(stock);
    }

    public void updateStock(Long id, UpdateStockRequest request) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stock not found with id: " + id));

        Integer newQuantity = stock.getQuantity() + request.quantity();
        stock.setQuantity(newQuantity);

        stockRepository.save(stock);
    }
}
