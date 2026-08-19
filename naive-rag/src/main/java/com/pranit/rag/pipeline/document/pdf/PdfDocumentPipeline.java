package com.pranit.rag.pipeline.document.pdf;

import com.pranit.rag.entities.DocumentType;
import com.pranit.rag.pipeline.document.DocumentPipeline;
import com.pranit.rag.pipeline.etl.pdf.PdfDocumentExtractor;
import com.pranit.rag.pipeline.etl.pdf.PdfDocumentLoader;
import com.pranit.rag.pipeline.etl.pdf.PdfDocumentTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PdfDocumentPipeline implements DocumentPipeline {

    private final PdfDocumentExtractor extractor;
    private final PdfDocumentTransformer transformer;
    private final PdfDocumentLoader loader;

    @Override
    public DocumentType getFileType() {
        return DocumentType.PDF;
    }

    @Override
    public void process(final Resource resource) {
        final var documents = extractor.extract(resource);
        final var transformed = transformer.transform(documents);
        loader.load(transformed);
    }
}
