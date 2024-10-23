package com.yuzarsif.userservice.dto;

import com.yuzarsif.userservice.model.Address;

public record AddressDto(
        Long id,
        String country,
        String city,
        String street,
        String zipCode,
        String apartmentNumber,
        String flatNumber
) {

    public static AddressDto convert(Address from) {
        return new AddressDto(
                from.getId(),
                from.getCountry(),
                from.getCity(),
                from.getStreet(),
                from.getZipCode(),
                from.getApartmentNumber(),
                from.getFlatNumber());
    }
}
