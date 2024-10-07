package com.yuzarsif.contextshare.productservice.service;

import com.yuzarsif.contextshare.productservice.dto.CreateProductRequest;
import com.yuzarsif.contextshare.productservice.dto.ProductDto;
import com.yuzarsif.contextshare.productservice.exception.EntityNotFoundException;
import com.yuzarsif.contextshare.productservice.model.Category;
import com.yuzarsif.contextshare.productservice.model.Product;
import com.yuzarsif.contextshare.productservice.repository.PhotoRepository;
import com.yuzarsif.contextshare.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveProduct_WhenCategoryExists_ShouldReturnProductDto() {

        when(categoryService.findById(anyInt())).thenReturn(mockCategory());

        when(productRepository.save(any(Product.class))).thenReturn(mockProduct());

        ProductDto response = productService.saveProduct(mockCreateProductRequest());

        assertNotNull(response);
        assertEquals("product_name", response.name());
        assertEquals("product_description", response.description());
        assertEquals(1000.0, response.price());
        assertEquals("main_image_url", response.mainImageUrl());
        assertEquals("category_name", response.category().name());
    }

    @Test
    public void testSaveProduct_WhenCategoryDoesNotExist_ShouldThrowEntityNotFoundException() {

        when(categoryService.findById(anyInt())).thenThrow(new EntityNotFoundException("Category didnt find by id: 1"));


        assertThrows(EntityNotFoundException.class, () -> productService.saveProduct(mockCreateProductRequest()));
    }

    @Test
    public void testGetProduct_WhenProductExists_ShouldReturnProductDto() {

        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(mockProduct()));

        ProductDto response = productService.findProductById(1L);

        assertNotNull(response);
        assertEquals("product_name", response.name());
        assertEquals("product_description", response.description());
        assertEquals(1000.0, response.price());
        assertEquals("main_image_url", response.mainImageUrl());
        assertEquals("category_name", response.category().name());
    }

    @Test
    public void testGetProduct_WhenProductDoesNotExist_ShouldThrowEntityNotFoundException() {

        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.findProductById(1L));
    }

    @Test
    public void testExistById_WhenProductExists_ShouldReturnTrue() {

        when(productRepository.existsById(anyLong())).thenReturn(true);

        assertTrue(productService.existById(1L));
    }

    @Test
    public void testExistById_WhenProductDoesNotExist_ShouldReturnFalse() {

        when(productRepository.existsById(anyLong())).thenReturn(false);

        assertFalse(productService.existById(1L));
    }

    private CreateProductRequest mockCreateProductRequest() {
        return new CreateProductRequest("product_name", "product_description", 1000.0, "main_image_url", 1);
    }

    private Product mockProduct() {
        return Product
                .builder()
                .id(1L)
                .name("product_name")
                .description("product_description")
                .price(1000.0)
                .mainImageUrl("main_image_url")
                .category(mockCategory())
                .build();
    }

    private Category mockCategory() {
        return Category
                .builder()
                .id(1)
                .name("category_name")
                .build();
    }
}
