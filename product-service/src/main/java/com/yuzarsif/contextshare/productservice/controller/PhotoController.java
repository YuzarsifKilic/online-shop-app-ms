package com.yuzarsif.contextshare.productservice.controller;

import com.yuzarsif.contextshare.productservice.dto.CreatePhotoRequest;
import com.yuzarsif.contextshare.productservice.dto.PhotoDto;
import com.yuzarsif.contextshare.productservice.service.PhotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping
    public ResponseEntity<List<PhotoDto>> createPhoto(@RequestBody @Valid CreatePhotoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(photoService.createPhoto(request));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<PhotoDto>> getPhotosByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(photoService.getPhotosByProductId(productId));
    }
}
