package com.yuzarsif.apigateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class RoleBasedGatewayFilter implements GlobalFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String httpMethod = request.getMethod().toString();
        System.out.println("HTTP Method: " + httpMethod);
        String role = request.getHeaders().getFirst("role");
        int start = role.indexOf("authority=") + 10;
        int end = role.indexOf("}", start);
        String authority = role.substring(start, end);
        String path = request.getPath().value();
        for (RoutePath routePath : RoutePath.getRoutes()) {
            if (path.startsWith(routePath.path()) && httpMethod.equals(routePath.method())) {
                if (authority.equals(routePath.role())) {
                    return chain.filter(exchange);
                } else {
                    return handleForbidden(exchange, routePath.httpStatus(), routePath.errorDescription());
                }
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> handleForbidden(ServerWebExchange exchange, HttpStatus httpStatus, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String jsonResponse = "{\"error\": \"" + message + "\"}";

        DataBufferFactory bufferFactory = response.bufferFactory();
        DataBuffer buffer = bufferFactory.wrap(jsonResponse.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}