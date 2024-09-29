package com.yuzarsif.orderservice.repository;

import com.yuzarsif.orderservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByUserId(Long userId);
}