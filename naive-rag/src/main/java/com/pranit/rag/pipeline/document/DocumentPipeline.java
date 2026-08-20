package com.pranit.rag.pipeline.document;

import com.pranit.rag.entities.DocumentType;
import org.springframework.core.io.Resource;

import java.util.UUID;

public interface DocumentPipeline {

    DocumentType getFileType();

    long process(UUID documentId, Resource resource);
}
