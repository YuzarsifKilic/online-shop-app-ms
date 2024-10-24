package com.yuzarsif.userservice.repository;

import com.yuzarsif.userservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> findByEmail(String email);

    List<Customer> findByIdIn(List<String> ids);
}
