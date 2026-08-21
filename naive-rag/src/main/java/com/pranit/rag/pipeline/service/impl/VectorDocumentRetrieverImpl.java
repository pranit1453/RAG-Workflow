package com.pranit.rag.pipeline.service.impl;

import com.pranit.rag.pipeline.service.VectorDocumentRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public final class VectorDocumentRetrieverImpl implements VectorDocumentRetriever {

    private final VectorStore vectorStore;

    @Override
    public Advisor retrieveDocument(final UUID documentId) {
        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder()
                        .vectorStore(this.vectorStore)
                        .topK(3)
                        .similarityThreshold(0.5)
                        .filterExpression(() ->
                                new FilterExpressionBuilder()
                                        .eq("documentId", documentId.toString())
                                        .build())
                        .build())
                .queryAugmenter(ContextualQueryAugmenter.builder()
                        .allowEmptyContext(false)
                        .build())
                .build();
    }
}
