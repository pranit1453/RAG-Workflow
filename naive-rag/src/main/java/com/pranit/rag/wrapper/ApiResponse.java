package com.pranit.rag.wrapper;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ApiResponse<T>(
        boolean status,
        String message,
        T data,
        Instant timestamp
) {
}
