package com.pranit.rag.ai.provider;

import com.pranit.rag.ai.dto.QueryResponse;
import com.pranit.rag.ai.stratergy.ChatModelStrategy;
import com.pranit.rag.entities.constant.Provider;
import com.pranit.rag.pipeline.service.VectorDocumentRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public final class AiService implements ChatModelStrategy {

    private final ChatClient chatClient;
    private final VectorDocumentRetriever vectorDocumentRetriever;

    @Value("classpath:prompt/userPrompt.st")
    private Resource userPrompt;

    @Override
    public QueryResponse getResponse(final String query, final UUID conversationId, final UUID documentId) {
        return this.chatClient.prompt()
                .advisors(spec -> spec.param(
                        ChatMemory.CONVERSATION_ID,
                        conversationId
                ))
                .advisors(vectorDocumentRetriever.retrieveDocument(documentId))
                .user(user -> user
                        .text(this.userPrompt)
                        .param("concept", query))
                .call()
                .entity(QueryResponse.class);
    }

    @Override
    public Flux<String> getStreamResponse(final String query, final UUID conversationId, final UUID documentId) {
        return this.chatClient.prompt()
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .advisors(vectorDocumentRetriever.retrieveDocument(documentId))
                .user(user -> user.text(this.userPrompt).param("concept", query))
                .stream()
                .content();
    }

    @Override
    public Provider getProviderName() {
        return Provider.NVIDIA;
    }
}
