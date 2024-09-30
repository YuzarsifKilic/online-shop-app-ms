package com.yuzarsif.userservice.dto;

import com.yuzarsif.userservice.model.Role;

public record AuthResponse(
        String token,
        Role role,
        String userId
) {
}
