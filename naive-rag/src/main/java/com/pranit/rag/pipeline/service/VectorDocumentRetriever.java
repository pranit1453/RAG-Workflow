package com.pranit.rag.pipeline.service;


import org.springframework.ai.chat.client.advisor.api.Advisor;

import java.util.UUID;

public interface VectorDocumentRetriever {

    Advisor retrieveDocument(UUID documentId);
}
