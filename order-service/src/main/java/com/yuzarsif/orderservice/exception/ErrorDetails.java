package com.yuzarsif.orderservice.exception;

import java.time.LocalDateTime;

public record ErrorDetails(
        String message,
        String description,
        LocalDateTime timestamp
) {
}
