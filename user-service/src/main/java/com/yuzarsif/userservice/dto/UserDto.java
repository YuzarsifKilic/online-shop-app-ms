package com.yuzarsif.userservice.dto;

import com.yuzarsif.userservice.model.Role;
import com.yuzarsif.userservice.model.User;

public record UserDto(
    String id,
    String firstName,
    String lastName,
    String email,
    Role role
) {

    public static UserDto convert(User from) {
        return new UserDto(
            from.getId(),
            from.getFirstName(),
            from.getLastName(),
            from.getEmail(),
            from.getRole());
    }
}
