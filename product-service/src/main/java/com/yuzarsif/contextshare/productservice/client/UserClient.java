package com.yuzarsif.contextshare.productservice.client;

import com.yuzarsif.contextshare.productservice.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserClient {

    private final RestTemplate restTemplate;

    public Boolean existCompanyById(String id) {
        try {
            return restTemplate.getForObject("http://USER-SERVICE/api/v1/companies/exists/{id}", Boolean.class, id);
        } catch (HttpClientErrorException ex) {
            throw new ClientException("Company client error occurred while checking company exist. Url: http://USER-SERVICE/api/v1/companies/exists/" + id);
        }
    }

    public CompanyResponse getCompanyById(String companyId) {
        try {
            return restTemplate.getForObject("http://USER-SERVICE/api/v1/companies/{id}", CompanyResponse.class, companyId);
        } catch (HttpClientErrorException ex) {
            throw new ClientException("Company client error occurred while getting company. Url: http://USER-SERVICE/api/v1/companies/" + companyId);
        }
    }
}
