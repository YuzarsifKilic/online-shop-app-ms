package com.yuzarsif.userservice.dto;

import jakarta.validation.constraints.NotNull;

public record CreateAddressRequest(
        String customerId,
        @NotNull
        String country,
        @NotNull
        String city,
        @NotNull
        String street,
        @NotNull
        String zipCode,
        @NotNull
        String apartmentNumber,
        @NotNull
        String flatNumber
) {
}
