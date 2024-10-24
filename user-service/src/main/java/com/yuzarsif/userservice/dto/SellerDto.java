package com.yuzarsif.userservice.dto;

import com.yuzarsif.userservice.model.Role;
import com.yuzarsif.userservice.model.Seller;

public record SellerDto(
    String id,
    String email,
    Role role,
    String companyName,
    String companyLogoUrl,
    AddressDto address
) {

    public static SellerDto convert(Seller from) {
        return new SellerDto(
                from.getId(),
                from.getEmail(),
                from.getRole(),
                from.getCompanyName(),
                from.getCompanyLogoUrl(),
                AddressDto.convert(from.getAddress()));
    }
}
