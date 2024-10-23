package com.yuzarsif.contextshare.productservice.repository;

import com.yuzarsif.contextshare.productservice.dto.ProductList;
import com.yuzarsif.contextshare.productservice.model.Product;
import com.yuzarsif.contextshare.productservice.spesification.ProductSpesification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("SELECT new com.yuzarsif.contextshare.productservice.dto.ProductList(p.id, p.name, p.price, p.mainImageUrl) FROM Product p")
    List<ProductList> findAllProductList(Specification<Product> spesification);

    @Query("SELECT new com.yuzarsif.contextshare.productservice.dto.ProductList(p.id, p.name, p.price, p.mainImageUrl) FROM Product p WHERE p.id IN (:idList)")
    List<ProductList> findByIdIn(List<Long> idList);
}
