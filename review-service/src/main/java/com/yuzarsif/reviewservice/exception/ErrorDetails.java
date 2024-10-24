package com.yuzarsif.reviewservice.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorDetails(
        String message,
        List<String> description,
        LocalDateTime timestamp
) {
}
