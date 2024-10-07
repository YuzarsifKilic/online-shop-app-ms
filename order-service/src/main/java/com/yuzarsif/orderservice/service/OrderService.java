package com.yuzarsif.orderservice.service;

import com.yuzarsif.orderservice.client.product.ProductClient;
import com.yuzarsif.orderservice.client.product.ProductResponse;
import com.yuzarsif.orderservice.client.stock.StockClient;
import com.yuzarsif.orderservice.client.stock.StockResponse;
import com.yuzarsif.orderservice.client.stock.UpdateStockRequest;
import com.yuzarsif.orderservice.dto.CreateOrderRequest;
import com.yuzarsif.orderservice.dto.OrderDto;
import com.yuzarsif.orderservice.exception.EntityNotFoundException;
import com.yuzarsif.orderservice.model.Order;
import com.yuzarsif.orderservice.model.Product;
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
    private final StockClient stockClient;

    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {

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

        Set<Product> productsList = new HashSet<>();

        for (int i = 0; i < products.size(); i++) {
            ProductResponse product = (ProductResponse) products.toArray()[i];
            Integer productQuantity = createOrderRequest.products().get(i).quantity();


            StockResponse productStock = stockClient.findStockByProductId(product.id());

            if (productStock.quantity() < productQuantity) {
                throw new EntityNotFoundException("Quantity not enough for product : " + product.name());
            }

            productsList.add(Product
                    .builder()
                    .product(product)
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

    public List<OrderDto> findOrdersByUserId(String userId) {
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
