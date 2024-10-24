package com.yuzarsif.userservice.controller;

import com.yuzarsif.userservice.dto.*;
import com.yuzarsif.userservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody @Valid CreateCustomerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(request));
    }

    @GetMapping("/exists/{customerId}")
    public ResponseEntity<Boolean> existById(@PathVariable String customerId) {
        return ResponseEntity.ok(customerService.checkCustomerExists(customerId));
    }

    @GetMapping("/validate")
    public ResponseEntity<ValidateCustomerDto> validateUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(customerService.validateToken(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(customerService.login(request));
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<List<CustomerDto>> getCustomerList(@PathVariable List<String> id) {
        return ResponseEntity.ok(customerService.getCustomerList(id));
    }
}
