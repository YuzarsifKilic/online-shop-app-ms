package com.yuzarsif.contextshare.productservice.dto;

import java.util.List;

public record CreateCampaignRequest(
    String name,
    String description,
    String startDate,
    String endDate,
    List<Long> products
) {
}
