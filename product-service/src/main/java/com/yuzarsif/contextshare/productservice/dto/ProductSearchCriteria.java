package com.yuzarsif.contextshare.productservice.dto;

public record ProductSearchCriteria(
    String name,
    Double min,
    Double max,
    Integer categoryId,
    String companyId
) {
}
