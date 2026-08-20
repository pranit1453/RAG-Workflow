package com.pranit.rag.document.service.impl;

import com.pranit.rag.document.dto.DocumentResponse;
import com.pranit.rag.document.exception.DocumentAlreadyExistsException;
import com.pranit.rag.document.exception.DocumentProcessingException;
import com.pranit.rag.document.repository.DocumentRepository;
import com.pranit.rag.document.service.DocumentStatusService;
import com.pranit.rag.document.service.DocumentUploadService;
import com.pranit.rag.entities.Document;
import com.pranit.rag.entities.FileStatus;
import com.pranit.rag.pipeline.factory.DocumentPipelineFactory;
import com.pranit.rag.wrapper.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class DocumentUploadServiceImpl implements DocumentUploadService {

    private final DocumentRepository documentRepository;
    private final DocumentPipelineFactory factory;
    private final DocumentStatusService documentStatusService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResponse<DocumentResponse> uploadDocument(final MultipartFile file) {
        if (documentRepository.findByFileName(file.getOriginalFilename())) {
            throw new DocumentAlreadyExistsException("File already exists");
        }
        final Document document = Document.builder()
                .fileName(file.getOriginalFilename())
                .fileSize(file.getSize())
                .fileStatus(FileStatus.UPLOADING)
                .chunksCreated(0)
                .build();
        final Document savedDocument = documentRepository.save(document);
        final Resource resource = file.getResource();
        documentStatusService.markProcessing(savedDocument.getDocumentId());
        final long chunkSize = factory.getPipeline(savedDocument.getDocumentId(), resource);
        if (chunkSize <= 0) {
            documentStatusService.markFailed(savedDocument.getDocumentId());
            throw new DocumentProcessingException("No chunks were created");
        }
        savedDocument.setChunksCreated(chunkSize);
        savedDocument.setFileStatus(FileStatus.INDEXED);
        final DocumentResponse response = DocumentResponse.builder()
                .documentId(savedDocument.getDocumentId())
                .fileName(savedDocument.getFileName())
                .fileSize(String.valueOf(savedDocument.getFileSize()))
                .status(savedDocument.getFileStatus())
                .chunksCreated(savedDocument.getChunksCreated())
                .build();
        return ApiResponse.<DocumentResponse>builder()
                .status(true)
                .message("Document uploaded and indexed successfully")
                .data(response)
                .timestamp(savedDocument.getCreatedAt())
                .build();
    }
}
