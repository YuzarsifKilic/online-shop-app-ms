package com.yuzarsif.contextshare.productservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateCampaignRequest(
    @NotNull(message = "Name cannot be null")
    String name,
    @NotNull(message = "Description cannot be null")
    String description,
    @NotNull(message = "Start date cannot be null")
    String startDate,
    @NotNull(message = "End date cannot be null")
    String endDate,
    @NotEmpty(message = "Products cannot be empty")
    List<Long> products
) {
}
