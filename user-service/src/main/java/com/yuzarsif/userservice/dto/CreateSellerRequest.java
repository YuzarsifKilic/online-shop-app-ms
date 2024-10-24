package com.yuzarsif.userservice.dto;

import com.yuzarsif.userservice.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record CreateSellerRequest(
        @NotNull @Email(message = "Email must be valid")
        String email,
        @NotNull @Length(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
        String password,
        @NotNull
        String companyName,
        @NotNull
        String companyLogoUrl,
        @NotNull @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
        String phoneNumber
) {
}
