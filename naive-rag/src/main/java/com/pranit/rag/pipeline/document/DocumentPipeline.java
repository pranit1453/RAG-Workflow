package com.pranit.rag.pipeline.document;

import com.pranit.rag.entities.DocumentType;
import org.springframework.core.io.Resource;

public interface DocumentPipeline {

    DocumentType getFileType();

    void process(Resource resource);
}
