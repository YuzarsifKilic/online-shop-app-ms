package com.yuzarsif.stockservice.controller;

import com.yuzarsif.stockservice.dto.CreateStockRequest;
import com.yuzarsif.stockservice.dto.StockDto;
import com.yuzarsif.stockservice.dto.UpdateStockRequest;
import com.yuzarsif.stockservice.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping
    public ResponseEntity<String> createStock(@RequestBody @Valid CreateStockRequest request) {
        stockService.createStock(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Stock created successfully");
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<StockDto> getStockByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(stockService.findStockByProductId(productId));
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateStock(@PathVariable Long id, @RequestBody @Valid UpdateStockRequest request) {
        stockService.updateStock(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Stock updated successfully");
    }
}
