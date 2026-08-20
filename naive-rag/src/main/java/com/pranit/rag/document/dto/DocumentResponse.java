package com.pranit.rag.document.dto;

import com.pranit.rag.entities.FileStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record DocumentResponse(
        UUID documentId,
        String fileName,
        String fileSize,
        FileStatus status,
        long chunksCreated
) {
}
