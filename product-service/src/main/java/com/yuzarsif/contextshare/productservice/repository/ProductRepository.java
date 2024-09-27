package com.yuzarsif.contextshare.productservice.repository;

import com.yuzarsif.contextshare.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
