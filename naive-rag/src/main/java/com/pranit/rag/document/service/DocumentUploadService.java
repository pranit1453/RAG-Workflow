package com.pranit.rag.document.service;

import com.pranit.rag.document.dto.DocumentResponse;
import com.pranit.rag.wrapper.ApiResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentUploadService {
    ApiResponse<DocumentResponse> uploadDocument(MultipartFile file);

    default ApiResponse<DocumentResponse> seedDocument(Resource resource) {
        return null;
    }
}
