package com.yuzarsif.cartservice.controller;

import com.yuzarsif.cartservice.dto.CartDto;
import com.yuzarsif.cartservice.dto.CreateCartRequest;
import com.yuzarsif.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(@RequestBody CreateCartRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.createCart(request));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CartDto> getCartByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(cartService.getCartByCustomerId(customerId));
    }
}
