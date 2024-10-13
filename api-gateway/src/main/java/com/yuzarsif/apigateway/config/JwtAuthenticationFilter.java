package com.yuzarsif.apigateway.config;

import com.yuzarsif.apigateway.security.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = jwtService.extractAllClaims(token);

                System.out.println(claims.get("roles"));

                exchange.getRequest().mutate()
                        .header("username", claims.getSubject())
                        .header("role", String.valueOf(claims.get("roles")))
                        .build();
            } catch (Exception e) {
                return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Geçersiz token"));
            }
        }

        return chain.filter(exchange);
    }
}