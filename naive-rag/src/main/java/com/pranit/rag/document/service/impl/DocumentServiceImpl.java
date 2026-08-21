package com.pranit.rag.document.service.impl;

import com.pranit.rag.document.dto.DocumentResponse;
import com.pranit.rag.document.exception.DocumentNotFoundException;
import com.pranit.rag.document.repository.DocumentRepository;
import com.pranit.rag.document.service.DocumentService;
import com.pranit.rag.document.specification.DocumentSpecification;
import com.pranit.rag.entities.entity.Document;
import com.pranit.rag.wrapper.ApiResponse;
import com.pranit.rag.wrapper.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public ApiResponse<DocumentResponse> fetchDocumentById(final UUID documentId) {
        final Document document = validateAndFetchDocumentById(documentId);
        return ApiResponse.<DocumentResponse>builder()
                .status(true)
                .message("Document fetched successfully")
                .data(DocumentResponse.builder()
                        .documentId(document.getDocumentId())
                        .fileName(document.getFileName())
                        .fileSize(document.getFileSize())
                        .status(document.getFileStatus())
                        .chunksCreated(document.getChunksCreated())
                        .createdAt(document.getCreatedAt())
                        .build())
                .timestamp(Instant.now())
                .build();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public PageResponse<DocumentResponse> fetchDocuments(final int page, final int size, final String keyword, final String sortBy, final String sortDirection) {
        final Sort sort = sortDirection.equalsIgnoreCase("ASC")
                ? Sort.by(Sort.Direction.ASC, sortBy)
                : Sort.by(Sort.Direction.DESC, sortBy);
        final Pageable pageable = PageRequest.of(page, size, sort);
        final Specification<Document> specification = DocumentSpecification.searchKeyword(keyword);
        final Page<Document> pages = documentRepository.findAll(specification, pageable);
        final List<DocumentResponse> content = pages.getContent()
                .stream()
                .map(document -> DocumentResponse.builder()
                        .documentId(document.getDocumentId())
                        .fileName(document.getFileName())
                        .fileSize(document.getFileSize())
                        .status(document.getFileStatus())
                        .chunksCreated(document.getChunksCreated())
                        .createdAt(document.getCreatedAt())
                        .build())
                .toList();
        return PageResponse.<DocumentResponse>builder()
                .content(content)
                .page(pages.getNumber())
                .size(pages.getSize())
                .totalElements(pages.getTotalElements())
                .totalPages(pages.getTotalPages())
                .last(pages.isLast())
                .build();
    }

    private Document validateAndFetchDocumentById(final UUID documentId) {
        return documentRepository.findByDocumentId(documentId)
                .orElseThrow(() -> {
                    log.warn("Document with id: {} not found", documentId);
                    return new DocumentNotFoundException("Document not found");
                });
    }
}
