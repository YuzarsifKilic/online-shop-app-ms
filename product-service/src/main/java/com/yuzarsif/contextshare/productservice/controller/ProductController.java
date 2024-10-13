package com.yuzarsif.contextshare.productservice.controller;

import com.yuzarsif.contextshare.productservice.dto.CreateProductRequest;
import com.yuzarsif.contextshare.productservice.dto.ProductDto;
import com.yuzarsif.contextshare.productservice.dto.ProductList;
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
    public ResponseEntity<List<ProductList>> getFilteredProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max,
            @RequestParam(required = false) Integer categoryId) {
        return ResponseEntity.ok(productService.getFilteredProducts(name, min, max, categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.existById(id));
    }
}
