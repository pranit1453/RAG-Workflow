package com.pranit.rag.ai.dto;

import lombok.Builder;

@Builder
public record QueryResponse(
        String message
) {
}
