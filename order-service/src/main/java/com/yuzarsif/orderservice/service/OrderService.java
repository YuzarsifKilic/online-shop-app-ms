package com.yuzarsif.orderservice.service;

import com.yuzarsif.orderservice.client.product.ProductClient;
import com.yuzarsif.orderservice.client.product.ProductResponse;
import com.yuzarsif.orderservice.dto.CreateOrderRequest;
import com.yuzarsif.orderservice.dto.OrderDto;
import com.yuzarsif.orderservice.exception.EntityNotFoundException;
import com.yuzarsif.orderservice.model.Order;
import com.yuzarsif.orderservice.model.Products;
import com.yuzarsif.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    public void createOrder(CreateOrderRequest createOrderRequest) {

        //TODO: Check user exists
        Set<ProductResponse> products = new HashSet<>();

        for (int i = 0; i < createOrderRequest.products().size(); i++) {
            Long productId = createOrderRequest.products().get(i).productId();

            Boolean existProduct = productClient.existById(productId);

            if (!existProduct) {
                throw new EntityNotFoundException("Product not found with id: " + productId);
            }

            products.add(productClient.getProductById(productId));
        }

        Set<Products> productsList = new HashSet<>();

        for (int i = 0; i < products.size(); i++) {
            productsList.add(Products
                    .builder()
                    .product((ProductResponse) products.toArray()[i])
                    .quantity(createOrderRequest.products().get(i).quantity())
                    .build());
        }


        Order order = Order
                .builder()
                .userId(createOrderRequest.userId())
                .products(productsList)
                .build();

        orderRepository.save(order);
    }

    public List<OrderDto> findOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderDto::convert)
                .toList();
    }

    public OrderDto findOrderById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
        return OrderDto.convert(order);
    }
}
