package com.yuzarsif.apigateway.config;

import org.springframework.http.HttpStatus;

import java.util.List;

public record RoutePath(
    String path,
    String role,
    String method,
    HttpStatus httpStatus,
    String errorDescription
) {

    public static List<RoutePath> getRoutes() {
        return List.of(
            new RoutePath("/api/v1/users/", "ROLE_ADMIN", "DELETE", HttpStatus.FORBIDDEN, "You are not authorized to delete users"),
            new RoutePath("/api/v1/products/", "ROLE_ADMIN", "DELETE", HttpStatus.FORBIDDEN, "You are not authorized to delete products")
        );
    }
}
