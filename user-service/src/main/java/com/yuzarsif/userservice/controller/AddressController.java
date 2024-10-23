package com.yuzarsif.userservice.controller;

import com.yuzarsif.userservice.dto.AddressDto;
import com.yuzarsif.userservice.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AddressDto>> getAddressByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(addressService.findAddressByCustomerId(customerId));
    }
}
