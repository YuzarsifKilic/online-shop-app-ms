package com.yuzarsif.userservice.repository;

import com.yuzarsif.userservice.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, String> {
}
