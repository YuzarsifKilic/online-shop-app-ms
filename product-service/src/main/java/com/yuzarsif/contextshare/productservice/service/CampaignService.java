package com.yuzarsif.contextshare.productservice.service;

import com.yuzarsif.contextshare.productservice.dto.CreateCampaignRequest;
import com.yuzarsif.contextshare.productservice.model.Campaign;
import com.yuzarsif.contextshare.productservice.model.Product;
import com.yuzarsif.contextshare.productservice.repository.CampaignRepository;
import com.yuzarsif.contextshare.productservice.utils.DateTimeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final ProductService productService;

    public void createCampaign(CreateCampaignRequest request) {
        Set<Product> products = new HashSet<>(productService.getProductsById(request.products()));

        Campaign campaign = Campaign
                .builder()
                .name(request.name())
                .description(request.description())
                .startDate(DateTimeConverter.toLocalDateTime(request.startDate()))
                .endDate(DateTimeConverter.toLocalDateTime(request.endDate()))
                .products(products)
                .build();

        campaignRepository.save(campaign);
    }
}
