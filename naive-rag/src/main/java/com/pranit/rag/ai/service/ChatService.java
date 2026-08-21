package com.pranit.rag.ai.service;

import com.pranit.rag.ai.dto.QueryResponse;
import com.pranit.rag.entities.constant.Provider;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ChatService {

    QueryResponse getResponseFromAssistant(Provider provider, String query, UUID conversationId, UUID documentId);

    Flux<String> getStreamResponseFromAssistant(Provider provider, String query, UUID conversationId, UUID documentId);
}
