package com.ilginbor.LibraryManagementSystem.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiErrorResponse(
        int status,
        String message,
        Map<String, String> fieldErrors,
        LocalDateTime timestamp
) {
}
