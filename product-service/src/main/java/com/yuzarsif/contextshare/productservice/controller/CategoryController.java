package com.yuzarsif.contextshare.productservice.controller;

import com.yuzarsif.contextshare.productservice.dto.CategoryDto;
import com.yuzarsif.contextshare.productservice.dto.CreateCategoryRequest;
import com.yuzarsif.contextshare.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CreateCategoryRequest request) {
        categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully");
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }
}
