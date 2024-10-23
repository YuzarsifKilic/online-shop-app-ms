package com.yuzarsif.contextshare.productservice.client;


public record AddressResponse(
        Long id,
        String country,
        String city,
        String street,
        String zipCode,
        String apartmentNumber,
        String flatNumber
) {
}
