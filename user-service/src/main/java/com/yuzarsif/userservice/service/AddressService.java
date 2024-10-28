package com.yuzarsif.userservice.service;

import com.yuzarsif.userservice.dto.AddressDto;
import com.yuzarsif.userservice.dto.CreateAddressRequest;
import com.yuzarsif.userservice.exception.EntityNotFoundException;
import com.yuzarsif.userservice.model.Address;
import com.yuzarsif.userservice.model.Customer;
import com.yuzarsif.userservice.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final CustomerService customerService;

    public AddressService(AddressRepository addressRepository, @Lazy CustomerService customerService) {
        this.addressRepository = addressRepository;
        this.customerService = customerService;
    }


    public AddressDto saveAddress(CreateAddressRequest request) {
        Customer customer = customerService.findCustomerById(request.customerId());

        Address address = Address
                .builder()
                .country(request.country())
                .city(request.city())
                .zipCode(request.zipCode())
                .street(request.street())
                .apartmentNumber(request.apartmentNumber())
                .flatNumber(request.flatNumber())
                .customer(customer)
                .build();

        return AddressDto.convert(addressRepository.save(address));
    }

    public List<AddressDto> findAddressByCustomerId(String customerId) {
        return addressRepository.findByCustomerId(customerId)
                .stream()
                .map(AddressDto::convert)
                .toList();
    }

    public Boolean checkAddressExists(Long addressId) {
        return addressRepository.existsById(addressId);
    }

    public AddressDto findAddressById(Long addressId) {
        return AddressDto.convert(addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + addressId)));
    }
}
