package com.yuzarsif.userservice.service;

import com.yuzarsif.userservice.dto.CreateSellerRequest;
import com.yuzarsif.userservice.dto.SellerDto;
import com.yuzarsif.userservice.exception.EntityNotFoundException;
import com.yuzarsif.userservice.model.Role;
import com.yuzarsif.userservice.model.Seller;
import com.yuzarsif.userservice.repository.SellerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;

    public SellerDto createCompany(@Valid CreateSellerRequest request) {

        Seller company = Seller
                .builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ROLE_SELLER)
                .companyName(request.companyName())
                .companyLogoUrl(request.companyLogoUrl())
                .phoneNumber(request.phoneNumber())
                .build();

        return SellerDto.convert(sellerRepository.save(company));
    }

    public SellerDto findCompanyById(String id) {
        return SellerDto.convert(sellerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Company not found with id: " + id)));
    }

    public Boolean checkCompanyExists(String id) {
        return sellerRepository.existsById(id);
    }
}
