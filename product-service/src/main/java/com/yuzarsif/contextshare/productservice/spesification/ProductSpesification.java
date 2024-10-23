package com.yuzarsif.contextshare.productservice.spesification;

import com.yuzarsif.contextshare.productservice.dto.ProductSearchCriteria;
import com.yuzarsif.contextshare.productservice.model.Category;
import com.yuzarsif.contextshare.productservice.model.Product;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpesification implements Specification<Product> {

    private final ProductSearchCriteria criteria;

    public ProductSpesification(ProductSearchCriteria criteria) {
        this.criteria = criteria;
    }
    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (criteria.name() != null && !criteria.name().isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("name"), criteria.name() + "%"));
        }

        if (criteria.min() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("price"), criteria.min()));
        }

        if (criteria.max() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("price"), criteria.max()));
        }

        if (criteria.categoryId() != null) {
            Join<Product, Category> categoryJoin = root.join("category");
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(categoryJoin.get("id"), criteria.categoryId()));
        }

        if (criteria.companyId() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("companyId"), criteria.companyId()));
        }

        return predicate;
    }
}
