package com.pranit.rag.pipeline.document.json;

import com.pranit.rag.entities.DocumentType;
import com.pranit.rag.pipeline.document.DocumentPipeline;
import com.pranit.rag.pipeline.etl.json.JsonDocumentExtractor;
import com.pranit.rag.pipeline.etl.json.JsonDocumentLoader;
import com.pranit.rag.pipeline.etl.json.JsonDocumentTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonDocumentPipeline implements DocumentPipeline {

    private final JsonDocumentExtractor extractor;
    private final JsonDocumentTransformer transformer;
    private final JsonDocumentLoader loader;

    @Override
    public DocumentType getFileType() {
        return DocumentType.JSON;
    }

    @Override
    public void process(final Resource resource) {
        IO.println("Processing file: " + resource.getFilename());
        final var documents = extractor.extract(resource);
        IO.println("After processing file: " + resource.getFilename());
        IO.println("Chunks: " + documents.size());
        documents.forEach(document -> IO.println("Document: " + document));
        IO.println("----------------------------------------------------------------");
        IO.println("Transforming file: " + resource.getFilename());
        final var transformed = transformer.transform(documents);
        IO.println("After transforming file: " + resource.getFilename());
        IO.println("Chunks: " + transformed.size());
        transformed.forEach(document -> IO.println("Document: " + document));
        IO.println("----------------------------------------------------------------");
        //loader.load(transformed);
    }
}
