package com.yuzarsif.contextshare.productservice.controller;

import com.yuzarsif.contextshare.productservice.dto.CreateProductRequest;
import com.yuzarsif.contextshare.productservice.dto.ProductDto;
import com.yuzarsif.contextshare.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid CreateProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(request));
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
