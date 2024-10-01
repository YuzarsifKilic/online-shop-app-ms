package com.yuzarsif.orderservice.client.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserClient {

    private final RestTemplate restTemplate;

    public Boolean checkUserExists(String userId) {
        return restTemplate.getForObject("http://USER-SERVICE/users/exists/{id}", Boolean.class, userId);
    }
}
