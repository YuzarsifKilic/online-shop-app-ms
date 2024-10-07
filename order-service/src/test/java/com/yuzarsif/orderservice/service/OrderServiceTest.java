package com.yuzarsif.orderservice.service;

import com.yuzarsif.orderservice.client.product.ProductClient;
import com.yuzarsif.orderservice.client.product.ProductResponse;
import com.yuzarsif.orderservice.client.stock.StockClient;
import com.yuzarsif.orderservice.client.stock.StockResponse;
import com.yuzarsif.orderservice.client.stock.UpdateStockRequest;
import com.yuzarsif.orderservice.dto.CreateOrderRequest;
import com.yuzarsif.orderservice.dto.OrderDto;
import com.yuzarsif.orderservice.dto.ProductRequest;
import com.yuzarsif.orderservice.exception.EntityNotFoundException;
import com.yuzarsif.orderservice.model.Order;
import com.yuzarsif.orderservice.model.Product;
import com.yuzarsif.orderservice.repository.OrderRepository;
import com.yuzarsif.orderservice.utils.OrderDataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductClient productClient;

    @Mock
    private StockClient stockClient;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void testCreateOrder_WhenProductExistAndStockAvailable_ShouldCreateOrderAndReturnOrderDto() {
        CreateOrderRequest createOrderRequest = mockCreateOrderRequest();

        when(productClient.existById(1L)).thenReturn(true);
        when(productClient.getProductById(1L)).thenReturn(mockProductResponse());
        when(stockClient.findStockByProductId(1L)).thenReturn(new StockResponse(1L, 20));

        Order savedOrder = mockOrder();
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        OrderDto result = orderService.createOrder(createOrderRequest);

        assertNotNull(result);
        assertEquals("user123", result.userId());
        verify(stockClient, times(1)).updateStock(eq(1L), any(UpdateStockRequest.class));
    }

    @Test
    public void testCreateOrder_WhenProductIdDoesNotExist_ShouldThrowEntityNotFoundException() {
        CreateOrderRequest createOrderRequest = mockCreateOrderRequest();

        when(productClient.existById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> orderService.createOrder(createOrderRequest));
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    public void testCreateOrder_WhenInsufficientStock_ShouldThrowEntityNotFoundException() {
        CreateOrderRequest createOrderRequest = mockCreateOrderRequest();

        when(productClient.existById(1L)).thenReturn(true);
        when(productClient.getProductById(1L)).thenReturn(mockProductResponse());
        when(stockClient.findStockByProductId(1L)).thenReturn(new StockResponse(1L, 5));

        assertThrows(EntityNotFoundException.class, () -> orderService.createOrder(createOrderRequest));
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    public void testFindOrdersByUserId_ShouldReturnOrderDtoList() {
        List<Order> orders = OrderDataUtils.getOrders();

        when(orderRepository.findByUserId("user_id")).thenReturn(orders);

        List<OrderDto> response = orderService.findOrdersByUserId("user_id");

        assertEquals(OrderDataUtils.getOrderDtoList(), response);
        verify(orderRepository, times(1)).findByUserId("user_id");
    }

    @Test
    public void testFindOrderById_WhenOrderExists_ShouldReturnOrderDto() {
        Order order = OrderDataUtils.getOrder();

        when(orderRepository.findById("order")).thenReturn(Optional.of(order));

        OrderDto orderDto = OrderDataUtils.getOrderDto();

        OrderDto response = orderService.findOrderById("order");

        assertEquals(orderDto, response);
        verify(orderRepository, times(1)).findById("order");
    }

    @Test
    public void testFindOrderById_WhenOrderDoesNotExist_ShouldThrowEntityNotFoundException() {
        when(orderRepository.findById("order")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.findOrderById("order"));

        verify(orderRepository, times(1)).findById("order");
    }

    private CreateOrderRequest mockCreateOrderRequest() {
        return new CreateOrderRequest(
                "user123",
                List.of(new ProductRequest(1L, 20)));
    }

    private ProductResponse mockProductResponse() {
        return new ProductResponse(1L, "Laptop", "High-end laptop", 1000.0, "image_url", null, Collections.emptyList());
    }

    private Order mockOrder() {
        ProductResponse productResponse = mockProductResponse();
        Product product = new Product("product_id", productResponse, 10);
        Set<Product> products = new HashSet<>();
        products.add(product);

        return Order.builder()
                .id("order1")
                .userId("user123")
                .products(products)
                .build();
    }
}
