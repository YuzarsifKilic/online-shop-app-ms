package com.yuzarsif.reviewservice.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserClient {

    private final RestTemplate restTemplate;

    public List<CustomerResponse> getCustomerList(List<String> id) {
        String baseUrl = "http://USER-SERVICE/api/v1/customers/list/{id}";

        String idPath = String.join(",", id);

        String uri = UriComponentsBuilder.fromUriString(baseUrl)
                .buildAndExpand(idPath)
                .toUriString();

        ResponseEntity<List<CustomerResponse>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CustomerResponse>>() {}
        );

        return response.getBody();
    }
}
