package com.yuzarsif.userservice.service;

import com.yuzarsif.userservice.dto.AddressDto;
import com.yuzarsif.userservice.model.Address;
import com.yuzarsif.userservice.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public List<AddressDto> findAddressByCustomerId(String customerId) {
        return addressRepository.findByCustomerId(customerId)
                .stream()
                .map(AddressDto::convert)
                .toList();
    }
}
