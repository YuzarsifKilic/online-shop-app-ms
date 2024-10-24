package com.yuzarsif.userservice.dto;

import com.yuzarsif.userservice.model.Address;

import java.util.List;
import java.util.Set;

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

    public static List<AddressDto> convert(Set<Address> from) {
        if (from == null) {
            return List.of();
        }
        return from.stream().map(AddressDto::convert).toList();
    }
}
