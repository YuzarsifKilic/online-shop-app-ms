package com.yuzarsif.contextshare.productservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuzarsif.contextshare.productservice.dto.*;
import com.yuzarsif.contextshare.productservice.exception.EntityNotFoundException;
import com.yuzarsif.contextshare.productservice.service.PhotoService;
import com.yuzarsif.contextshare.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    public void testCreateProduct_WhenRequestIsValid_ShouldReturnProductDto() throws Exception {

        when(productService.saveProduct(mockCreateProductRequest())).thenReturn(mockProductDto());

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateProductRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(mockProductDto())))
                .andExpect(jsonPath("$.name").value("product_name"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("product_description"))
                .andExpect(jsonPath("$.price").value(1000.0))
                .andExpect(jsonPath("$.mainImageUrl").value("main_image_url"));
    }

    @Test
    public void testCreateProduct_WhenCategoryDoesNotExist_ShouldThrowEntityNotFoundException() throws Exception {

        when(productService.saveProduct(mockCreateProductRequest())).thenThrow(new EntityNotFoundException("Category not found with id: 1"));

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockCreateProductRequest())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("NOT_FOUND_ERROR"))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void testCreateProduct_WhenRequestIsInvalid_ShouldReturnBadRequest() throws Exception {

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockCreateProductRequestInvalid())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void testGetProduct_WhenProductExists_ShouldReturnProductDto() throws Exception {

        when(productService.findProductById(1L)).thenReturn(mockProductDto());

        mockMvc.perform(get("/api/v1/products/{productId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockProductDto())))
                .andExpect(jsonPath("$.name").value("product_name"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("product_description"))
                .andExpect(jsonPath("$.price").value(1000.0))
                .andExpect(jsonPath("$.mainImageUrl").value("main_image_url"));
    }

    @Test
    public void testGetProduct_WhenProductDoesNotExist_ShouldThrowEntityNotFoundException() throws Exception {

        when(productService.findProductById(1L)).thenThrow(new EntityNotFoundException("Product not found with id: 1"));

        mockMvc.perform(get("/api/v1/products/{productId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("NOT_FOUND_ERROR"))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void testExistById_WhenProductExists_ShouldReturnTrue() throws Exception {

        when(productService.existById(1L)).thenReturn(true);

        mockMvc.perform(get("/api/v1/products/exists/{productId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void testExistById_WhenProductDoesNotExist_ShouldReturnFalse() throws Exception {

        when(productService.existById(1L)).thenReturn(false);

        mockMvc.perform(get("/api/v1/products/exists/{productId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(false));
    }

    private CreateProductRequest mockCreateProductRequestInvalid() {
        return new CreateProductRequest("product_name", "product_description", -1000.0, "main_image_url", 1);
    }

    private ProductDto mockProductDto() {
        return new ProductDto(1L, "product_name", "product_description", 1000.0, "main_image_url", mockCategoryDto(), List.of(mockPhotoDto()));
    }

    private CampaignDto mockCampaignDto() {
        return new CampaignDto(1, "campaign_name", "campaign_description", LocalDateTime.parse("2024-10-05T13:48"), LocalDateTime.parse("2024-10-05T13:48"), List.of(mockProductDto()));
    }

    private CategoryDto mockCategoryDto() {
        return new CategoryDto(1, "category_name");
    }

    private PhotoDto mockPhotoDto() {
        return new PhotoDto(1L, "photo_url", 1);
    }

    private CreateProductRequest mockCreateProductRequest() {
        return new CreateProductRequest("product_name", "product_description", 1000.0, "main_image_url", 1);
    }
}
