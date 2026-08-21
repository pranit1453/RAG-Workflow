package com.pranit.rag.document.service;

import com.pranit.rag.document.dto.DocumentResponse;
import com.pranit.rag.wrapper.ApiResponse;
import com.pranit.rag.wrapper.PageResponse;

import java.util.UUID;

public interface DocumentService {
    ApiResponse<DocumentResponse> fetchDocumentById(UUID documentId);

    PageResponse<DocumentResponse> fetchDocuments(int page, int size, String keyword, String sortBy, String sortDirection);
}
