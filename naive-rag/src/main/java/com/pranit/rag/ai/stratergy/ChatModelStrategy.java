package com.pranit.rag.ai.stratergy;


import com.pranit.rag.ai.dto.QueryResponse;
import com.pranit.rag.entities.constant.Provider;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ChatModelStrategy {

    QueryResponse getResponse(String query, UUID conversationId, UUID documentId);

    Flux<String> getStreamResponse(String query, UUID conversationId, UUID documentId);

    Provider getProviderName();
}
