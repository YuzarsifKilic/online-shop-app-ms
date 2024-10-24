package com.yuzarsif.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        System.out.println(builder);
        return builder.routes()
                .route("user-service-route", r -> r.path("/api/v1/users/**", "/api/v1/customers/**", "/api/v1/companies", "/api/v1/companies/**", "/api/v1/addresses/**")
                        .uri("lb://USER-SERVICE"))
                .route("product-service-route", r -> r.path("/api/v1/products/**", "/api/v1/categories/**", "/api/v1/campaigns/**", "/api/v1/photos/**")
                        .uri("lb://PRODUCT-SERVICE"))
                .route("order-service-route", r -> r.path("/api/v1/orders/**")
                        .uri("lb://ORDER-SERVICE"))
                .route("stock-service-route", r -> r.path("/api/v1/stocks/**")
                        .uri("lb://STOCK-SERVICE"))
                .route("cart-service-route", r -> r.path("/api/v1/carts/**")
                        .uri("lb://CART-SERVICE"))
                .route("review-service-route", r -> r.path("/api/v1/reviews/**")
                        .uri("lb://REVIEW-SERVICE"))
                .build();
    }

    @Bean
    public RoleBasedGatewayFilter roleBasedGatewayFilter() {
        return new RoleBasedGatewayFilter();
    }
}
