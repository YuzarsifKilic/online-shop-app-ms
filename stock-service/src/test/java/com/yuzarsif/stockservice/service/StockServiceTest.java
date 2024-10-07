package com.yuzarsif.stockservice.service;

import com.yuzarsif.stockservice.client.ProductClient;
import com.yuzarsif.stockservice.dto.CreateStockRequest;
import com.yuzarsif.stockservice.dto.StockDto;
import com.yuzarsif.stockservice.dto.UpdateStockRequest;
import com.yuzarsif.stockservice.exception.EntityNotFoundException;
import com.yuzarsif.stockservice.model.Stock;
import com.yuzarsif.stockservice.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StockServiceTest {

    @InjectMocks
    private StockService stockService;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private ProductClient productClient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStock_WhenProductExists_ShouldCreateStock() {

        when(productClient.existById(1L)).thenReturn(true);
        when(stockRepository.save(any(Stock.class))).thenReturn(mockStock());

        StockDto result = stockService.createStock(new CreateStockRequest(1L, 100));

        assertNotNull(result);
        assertEquals(1L, result.productId());
        assertEquals(100, result.quantity());
        verify(stockRepository, times(1)).save(any(Stock.class));
    }

    @Test
    void testCreateStock_WhenProductDoesNotExist_ShouldThrowEntityNotFoundException() {

        when(productClient.existById(1L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                stockService.createStock(new CreateStockRequest(1L, 100))
        );

        assertEquals("Product not found with id: 1", exception.getMessage());
        verify(stockRepository, never()).save(any(Stock.class));
    }

    @Test
    void testFindStockByProductId_WhenStockExists_ShouldReturnStockDto() {

        when(stockRepository.findByProductId(1L)).thenReturn(Optional.of(mockStock()));

        StockDto result = stockService.findStockByProductId(1L);

        assertNotNull(result);
        assertEquals(1L, result.productId());
        assertEquals(100, result.quantity());
    }

    @Test
    void testFindStockByProductId_WhenStockDoesNotExist_ShouldThrowEntityNotFoundException() {

        when(stockRepository.findByProductId(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                stockService.findStockByProductId(1L)
        );

        assertEquals("Stock not found by product id : 1", exception.getMessage());
    }

    @Test
    void testUpdateStock_WhenStockExists_ShouldUpdateStock() {

        Stock existingStock = mockStock();
        when(stockRepository.findByProductId(1L)).thenReturn(Optional.of(existingStock));
        when(stockRepository.save(any(Stock.class))).thenReturn(existingStock);

        StockDto result = stockService.updateStock(1L, new UpdateStockRequest(50));

        assertNotNull(result);
        assertEquals(150, result.quantity());
        verify(stockRepository, times(1)).save(existingStock);
    }

    @Test
    void testUpdateStock_WhenStockDoesNotExist_ShouldThrowEntityNotFoundException() {

        when(stockRepository.findByProductId(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                stockService.updateStock(1L, new UpdateStockRequest(50))
        );

        assertEquals("Stock not found with product id: 1", exception.getMessage());
    }

    private Stock mockStock() {
        return Stock.builder()
                .id(1L)
                .productId(1L)
                .quantity(100)
                .build();
    }


}
