package com.yuzarsif.userservice.controller;

import com.yuzarsif.userservice.dto.AddressDto;
import com.yuzarsif.userservice.dto.CreateAddressRequest;
import com.yuzarsif.userservice.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDto> saveAddress(@RequestBody @Valid CreateAddressRequest request) {
        return ResponseEntity.ok(addressService.saveAddress(request));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AddressDto>> getAddressByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(addressService.findAddressByCustomerId(customerId));
    }
}
