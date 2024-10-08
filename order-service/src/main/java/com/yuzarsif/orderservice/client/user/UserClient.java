package com.yuzarsif.orderservice.client.user;

import com.yuzarsif.orderservice.exception.ClientException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserClient {

    private final RestTemplate restTemplate;

    public Boolean checkUserExists(String userId) {
        try {
            return restTemplate.getForObject("http://USER-SERVICE/users/exists/{id}", Boolean.class, userId);
        } catch (HttpClientErrorException ex) {
            throw new ClientException(String.format(
                    "An error occurred while trying to access user information. " +
                    "Url: http://USER-SERVICE/users/exists/%s", userId));
        }
    }
}
