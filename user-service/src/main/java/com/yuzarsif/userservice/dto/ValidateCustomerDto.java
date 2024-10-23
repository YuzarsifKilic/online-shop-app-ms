package com.yuzarsif.userservice.dto;

public record ValidateCustomerDto(
    String id,
    String firstName,
    String lastName,
    String email
) {
}
