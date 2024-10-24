package com.yuzarsif.orderservice.controller;

import com.yuzarsif.orderservice.client.product.CategoryResponse;
import com.yuzarsif.orderservice.client.product.PhotoResponse;
import com.yuzarsif.orderservice.client.product.ProductResponse;
import com.yuzarsif.orderservice.dto.OrderDto;
import com.yuzarsif.orderservice.model.Product;
import com.yuzarsif.orderservice.service.OrderService;
import com.yuzarsif.orderservice.utils.OrderDataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    public void testCreateOrder() {
        OrderDto orderDto = OrderDataUtils.getOrderDto();

        when(orderService.createOrder(OrderDataUtils.getCreateOrderRequest())).thenReturn(orderDto);

        ResponseEntity<OrderDto> response = orderController.createOrder(OrderDataUtils.getCreateOrderRequest());

        verify(orderService, times(1)).createOrder(OrderDataUtils.getCreateOrderRequest());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(orderDto, response.getBody());
    }

    @Test
    public void testGetOrderById() {
        OrderDto orderDto = new OrderDto("order", "user_id", OrderDataUtils.getProducts());

        when(orderService.findOrderById("order")).thenReturn(orderDto);

        ResponseEntity<OrderDto> response = orderController.getOrderById("order");

        verify(orderService, times(1)).findOrderById("order");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderDto, response.getBody());
    }

    @Test
    public void testGetOrdersByUserId() {
//        List<OrderDto> orderList = OrderDataUtils.getOrderDtoList();
//
//        when(orderService.findOrdersByUserId("user_id")).thenReturn(orderList);
//
//        ResponseEntity<List<OrderDto>> orderResponse = orderController.getOrdersByUserId("user_id");
//
//        verify(orderService, times(1)).findOrdersByUserId("user_id");
//
//        assertEquals(HttpStatus.OK, orderResponse.getStatusCode());
//        assertEquals(orderList, orderResponse.getBody());
    }
}
