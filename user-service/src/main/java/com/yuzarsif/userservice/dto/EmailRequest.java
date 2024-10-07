package com.yuzarsif.userservice.dto;

import jakarta.validation.constraints.Email;

public record EmailRequest(
        @Email(message = "Email must be valid")
        String email
) {
}
