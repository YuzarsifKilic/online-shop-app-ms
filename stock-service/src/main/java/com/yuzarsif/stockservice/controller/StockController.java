package com.yuzarsif.stockservice.controller;

import com.yuzarsif.stockservice.dto.CreateStockRequest;
import com.yuzarsif.stockservice.dto.UpdateStockRequest;
import com.yuzarsif.stockservice.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping
    public void createStock(@RequestBody @Valid CreateStockRequest request) {
        stockService.createStock(request);
    }

    @PutMapping("/{id}")
    public void updateStock(@PathVariable Long id, @RequestBody @Valid UpdateStockRequest request) {
        stockService.updateStock(id, request);
    }
}
