package com.yuzarsif.contextshare.productservice.dto;

import com.yuzarsif.contextshare.productservice.model.Campaign;

import java.time.LocalDateTime;
import java.util.List;

public record CampaignDto(
        Integer id,
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        List<ProductDto> products
) {

    public static CampaignDto convert(Campaign from) {
        return new CampaignDto(
                from.getId(),
                from.getName(),
                from.getDescription(),
                from.getStartDate(),
                from.getEndDate(),
                ProductDto.convert(from.getProducts()));
    }

    public static List<CampaignDto> convert(List<Campaign> campaigns) {
        return campaigns
                .stream()
                .map(CampaignDto::convert)
                .toList();
    }
}
