package com.yuzarsif.userservice.repository;

import com.yuzarsif.userservice.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByCustomerId(String customerId);
}
