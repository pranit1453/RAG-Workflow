package com.pranit.rag.pipeline.etl.pdf;

import com.pranit.rag.pipeline.etl.Loader;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public final class PdfDocumentLoader implements Loader {

    private final VectorStore vectorStore;

    @Override
    public void load(final List<Document> documents) {
        this.vectorStore.add(documents);
    }
}
