package com.yuzarsif.userservice.controller;

import com.yuzarsif.userservice.dto.CreateSellerRequest;
import com.yuzarsif.userservice.dto.SellerDto;
import com.yuzarsif.userservice.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @PostMapping
    public ResponseEntity<SellerDto> createCompany(@RequestBody @Valid CreateSellerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sellerService.createCompany(request));
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<SellerDto> getCompanyById(@PathVariable String companyId) {
        return ResponseEntity.ok(sellerService.findCompanyById(companyId));
    }

    @GetMapping("/exists/{companyId}")
    public ResponseEntity<Boolean> existById(@PathVariable String companyId) {
        return ResponseEntity.ok(sellerService.checkCompanyExists(companyId));
    }
}
