package com.yuzarsif.userservice.dto;

public record LoginRequest(
        String email,
        String password
) {
}
