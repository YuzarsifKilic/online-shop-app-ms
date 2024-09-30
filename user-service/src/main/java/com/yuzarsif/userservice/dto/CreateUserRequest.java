package com.yuzarsif.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CreateUserRequest(
        @NotNull @Length(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
        String firstName,
        @NotNull @Length(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
        String lastName,
        @NotNull @Email(message = "Email must be valid")
        String email,
        @NotNull @Length(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
        String password
) {
}
