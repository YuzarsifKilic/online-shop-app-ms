package com.yuzarsif.contextshare.productservice.controller;

import com.yuzarsif.contextshare.productservice.dto.CreateProductRequest;
import com.yuzarsif.contextshare.productservice.dto.ProductDto;
import com.yuzarsif.contextshare.productservice.dto.ProductList;
import com.yuzarsif.contextshare.productservice.dto.ProductSearchCriteria;
import com.yuzarsif.contextshare.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid CreateProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductList>> getFilteredProducts(@ModelAttribute ProductSearchCriteria criteria) {
        return ResponseEntity.ok(productService.getFilteredProducts(criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.existById(id));
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<List<ProductList>> getProductsById(@PathVariable List<Long> id) {
        return ResponseEntity.ok(productService.getProductListById(id));
    }
}
