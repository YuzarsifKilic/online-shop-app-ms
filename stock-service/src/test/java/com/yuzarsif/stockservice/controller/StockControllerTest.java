package com.yuzarsif.stockservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuzarsif.stockservice.client.ProductClient;
import com.yuzarsif.stockservice.dto.CreateStockRequest;
import com.yuzarsif.stockservice.dto.StockDto;
import com.yuzarsif.stockservice.dto.UpdateStockRequest;
import com.yuzarsif.stockservice.exception.EntityNotFoundException;
import com.yuzarsif.stockservice.repository.StockRepository;
import com.yuzarsif.stockservice.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StockService stockService;

    @MockBean
    private ProductClient productClient;

    @Test
    public void testCreateStock_WhenRequestValid_ShouldCreateStockAndReturnStockDto() throws Exception {

        when(productClient.existById(1L)).thenReturn(true);

        when(stockService.createStock(any(CreateStockRequest.class))).thenReturn(mockStockDto());

        mockMvc.perform(post("/api/v1/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateStockRequest()))
        ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.quantity").value(20));
    }

    @Test
    public void testCreateStock_WhenRequestInvalid_ShouldReturnBadRequest() throws Exception {

        mockMvc.perform(post("/api/v1/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockCreateStockRequestInvalid())))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.description[0]").value("productId: Product id cannot be null"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void testGetStockByProductId_WhenProductExist_ShouldReturnStockDto() throws Exception {

        when(stockService.findStockByProductId(1L)).thenReturn(mockStockDto());

        mockMvc.perform(get("/api/v1/stocks/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.quantity").value(20));
    }

    @Test
    public void testGetStockByProductId_WhenProductDoesNotExist_ShouldThrowEntityNotFoundException() throws Exception {

        when(stockService.findStockByProductId(anyLong())).thenThrow(new EntityNotFoundException("Stock not found by product id: 1"));

        mockMvc.perform(get("/api/v1/stocks/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("NOT_FOUND_ERROR"))
                .andExpect(jsonPath("$.description[0]").value("Stock not found by product id: 1"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void testUpdateStock_WhenRequestValidAndStockExist_ShouldUpdateStockAndReturnStockDto() throws Exception {

        when(stockService.updateStock(anyLong(), any(UpdateStockRequest.class))).thenReturn(mockStockDto());

        mockMvc.perform(put("/api/v1/stocks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUpdateStockRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(1L))
                .andExpect(jsonPath("$.quantity").value(20));
    }

    @Test
    public void testUpdateStock_WhenStockDoesNotExist_ShouldThrowEntityNotFoundException() throws Exception {

        when(stockService.updateStock(anyLong(), any(UpdateStockRequest.class))).thenThrow(new EntityNotFoundException("Stock not found by product id: 1"));

        mockMvc.perform(put("/api/v1/stocks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUpdateStockRequest())))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("NOT_FOUND_ERROR"))
                .andExpect(jsonPath("$.description[0]").value("Stock not found by product id: 1"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    private CreateStockRequest mockCreateStockRequest() {
        return new CreateStockRequest(1L, 20);
    }

    private CreateStockRequest mockCreateStockRequestInvalid() {
        return new CreateStockRequest(null, 20);
    }

    private StockDto mockStockDto() {
        return new StockDto(1L, 1L, 20);
    }

    private UpdateStockRequest mockUpdateStockRequest() {
        return new UpdateStockRequest(20);
    }


}
