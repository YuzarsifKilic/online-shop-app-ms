package com.yuzarsif.userservice.service;

import com.yuzarsif.userservice.dto.*;
import com.yuzarsif.userservice.model.Address;
import com.yuzarsif.userservice.model.Customer;
import com.yuzarsif.userservice.model.Role;
import com.yuzarsif.userservice.model.User;
import com.yuzarsif.userservice.repository.CustomerRepository;
import com.yuzarsif.userservice.security.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public CustomerDto createCustomer(CreateCustomerRequest request) {
        Customer customer = Customer
                .builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.ROLE_CUSTOMER)
                .phoneNumber(request.phoneNumber())
                .build();

        return CustomerDto.convert(customerRepository.save(customer));
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        if (authentication.isAuthenticated()) {
            Customer customer = customerRepository.findByEmail(request.email()).get();
            String token = jwtService.generateTokenForCustomer(customer);
            return new AuthResponse(
                    token,
                    customer.getRole(),
                    customer.getId());
        }
        throw new UsernameNotFoundException("Invalid credentials");
    }

    public Boolean checkCustomerExists(String id) {
        return customerRepository.existsById(id);
    }

    public ValidateCustomerDto validateToken(String token) {
        token = token.substring(7);
        Claims claims = jwtService.extractAllClaims(token);

        System.out.println(claims.toString());

        return new ValidateCustomerDto(
                claims.get("userId").toString(),
                claims.get("firstName").toString(),
                claims.get("lastName").toString(),
                claims.get("email").toString());
    }

    protected Customer findCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found with id: " + id));
    }
}
