package com.yuzarsif.contextshare.productservice.client;


public record CompanyResponse(
    String id,
    String email,
    String companyName,
    String companyLogoUrl,
    AddressResponse address
) {
}
