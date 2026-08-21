package com.pranit.rag.document.dto;

import com.pranit.rag.entities.constant.FileStatus;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record DocumentResponse(
        UUID documentId,
        String fileName,
        long fileSize,
        FileStatus status,
        long chunksCreated,
        Instant createdAt
) {
}
