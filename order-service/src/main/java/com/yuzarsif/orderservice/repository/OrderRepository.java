package com.yuzarsif.orderservice.repository;

import com.yuzarsif.orderservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByUserId(String userId);

    @Query("{ 'userId': ?0, 'products.product.id': ?1 }")
    List<Order> findByUserIdAndProductId(String userId, Long productId);
}
