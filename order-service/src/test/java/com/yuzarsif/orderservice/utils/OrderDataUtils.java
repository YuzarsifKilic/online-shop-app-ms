package com.yuzarsif.orderservice.utils;

import com.yuzarsif.orderservice.client.product.CategoryResponse;
import com.yuzarsif.orderservice.client.product.PhotoResponse;
import com.yuzarsif.orderservice.client.product.ProductResponse;
import com.yuzarsif.orderservice.dto.CreateOrderRequest;
import com.yuzarsif.orderservice.dto.OrderDto;
import com.yuzarsif.orderservice.dto.ProductRequest;
import com.yuzarsif.orderservice.model.Order;
import com.yuzarsif.orderservice.model.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderDataUtils {

    public static CategoryResponse getCategoryResponse() {
        return new CategoryResponse(1, "Technology");
    }

    public static List<PhotoResponse> getPhotoResponses() {
        List<PhotoResponse> photoResponseList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PhotoResponse photoResponse = new PhotoResponse(Integer.toUnsignedLong(i), "image_url_" + i, i + 1);
            photoResponseList.add(photoResponse);
        }
        return photoResponseList;
    }

    public static ProductResponse getProduct(String name, String description, Double price, String mainImageUrl) {
        return new ProductResponse(1L, name, description, price, mainImageUrl, getCategoryResponse(), getPhotoResponses());
    }

    public static Set<Product> getProducts() {
        Set<Product> products = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            ProductResponse productResponse = getProduct("name_" + i, "description_" + i, i + 1 * 100.0, "image_url_" + i);
            Product product = new Product();
            product.setProduct(productResponse);
            product.setQuantity(i + 1);
            products.add(product);
        }
        return products;
    }

    public static List<OrderDto> getOrderDtoList() {
        List<OrderDto> orders = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            OrderDto orderDto = new OrderDto("order_" + i, "user_id", getProducts());
            orders.add(orderDto);
        }
        return orders;
    }

    public static OrderDto getOrderDto() {
        return new OrderDto("order", "user_id", getProducts());
    }

    public static List<ProductRequest> getProductRequest() {
        List<ProductRequest> productRequestList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProductRequest productRequest = new ProductRequest(1L, i + 1);
            productRequestList.add(productRequest);
        }
        return productRequestList;
    }

    public static CreateOrderRequest getCreateOrderRequest() {
        return new CreateOrderRequest("user", getProductRequest());
    }

    public static Order getOrder() {
        return new Order("order", "user_id", getProducts());
    }

    public static List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Order order = new Order("order_" + i, "user_id", getProducts());
            orders.add(order);
        }
        return orders;
    }
}
