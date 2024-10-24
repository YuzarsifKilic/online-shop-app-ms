package com.yuzarsif.orderservice.service;

import com.yuzarsif.orderservice.client.product.ProductClient;
import com.yuzarsif.orderservice.client.product.ProductResponse;
import com.yuzarsif.orderservice.client.stock.StockClient;
import com.yuzarsif.orderservice.client.stock.StockResponse;
import com.yuzarsif.orderservice.client.stock.UpdateStockRequest;
import com.yuzarsif.orderservice.client.user.UserClient;
import com.yuzarsif.orderservice.dto.CreateOrderRequest;
import com.yuzarsif.orderservice.dto.OrderDto;
import com.yuzarsif.orderservice.dto.OrderResponse;
import com.yuzarsif.orderservice.exception.EntityNotFoundException;
import com.yuzarsif.orderservice.model.Order;
import com.yuzarsif.orderservice.model.Product;
import com.yuzarsif.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final StockClient stockClient;
    private final UserClient userClient;

    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {

        if (!userClient.checkUserExists(createOrderRequest.userId())) {
            throw new EntityNotFoundException("User not found with id : " + createOrderRequest.userId());
        }

        for (int i = 0; i < createOrderRequest.products().size(); i++) {
            Long productId = createOrderRequest.products().get(i).productId();

            Boolean existProduct = productClient.existById(productId);

            if (!existProduct) {
                throw new EntityNotFoundException("Product not found with id: " + productId);
            }
        }

        Set<Product> productsList = new HashSet<>();

        for (int i = 0; i < createOrderRequest.products().size(); i++) {
            Integer productQuantity = createOrderRequest.products().get(i).quantity();
            Long productId = createOrderRequest.products().get(i).productId();


            StockResponse productStock = stockClient.findStockByProductId(productId);

            if (productStock.quantity() < productQuantity) {
                throw new EntityNotFoundException("Quantity not enough for product id : " + productId);
            }

            productsList.add(Product
                    .builder()
                    .productId(productId)
                    .quantity(productQuantity)
                    .build());
        }


        Order order = Order
                .builder()
                .userId(createOrderRequest.userId())
                .products(productsList)
                .build();

        Order savedOrder = orderRepository.save(order);

        reduceProductQuantity(createOrderRequest);

        return OrderDto.convert(savedOrder);
    }

    private void reduceProductQuantity(CreateOrderRequest request) {
        for (int i = 0; i < request.products().size(); i++) {
            Long productId = request.products().get(i).productId();
            Integer productQuantity = request.products().get(i).quantity();

            stockClient.updateStock(productId, new UpdateStockRequest(-productQuantity));
        }

    }

    public List<OrderResponse> findOrdersByUserId(String userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        List<OrderResponse> orderResponseList = new ArrayList<>();

        for (Order order : orders) {
            List<Long> productIds = order.getProducts().stream().map(Product::getProductId).toList();
            List<ProductResponse> products = productClient.getProductById(productIds);
            OrderResponse orderResponse = new OrderResponse(order.getId(), order.getUserId(), products);
            orderResponseList.add(orderResponse);
        }

        return orderResponseList;
    }

    public OrderDto findOrderById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
        return OrderDto.convert(order);
    }

    public Boolean checkOrderExists(String customerId, Long productId) {
        return orderRepository.findByUserIdAndProductId(customerId, productId).size() > 0;
    }
}
