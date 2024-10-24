package com.yuzarsif.userservice.dto;

import com.yuzarsif.userservice.model.Customer;
import com.yuzarsif.userservice.model.Role;

import java.util.List;

public record CustomerDto(
        String id,
        String firstName,
        String lastName,
        String email,
        Role role,
        String phoneNumber,
        List<AddressDto> addresses
) {

    public static CustomerDto convert(Customer from) {
        return new CustomerDto(
                from.getId(),
                from.getFirstName(),
                from.getLastName(),
                from.getEmail(),
                from.getRole(),
                from.getPhoneNumber(),
                AddressDto.convert(from.getAddresses()));
    }
}
