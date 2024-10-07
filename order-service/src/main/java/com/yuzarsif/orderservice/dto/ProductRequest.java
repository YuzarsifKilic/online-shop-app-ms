package com.yuzarsif.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public record ProductRequest(
        @NotNull
        Long productId,
        @NotNull @Min(value = 1, message = "Quantity should be greater than or equal to 1")
        Integer quantity
) {

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                ProductRequest that = (ProductRequest) o;
                return Objects.equals(productId, that.productId) && Objects.equals(quantity, that.quantity);
        }

        @Override
        public int hashCode() {
                return Objects.hash(productId, quantity);
        }
}
