package com.pranit.rag.controller;

import com.pranit.rag.ai.dto.QueryResponse;
import com.pranit.rag.ai.service.ChatService;
import com.pranit.rag.entities.constant.Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RestController
@RequestMapping("/api/chat/documents")
@RequiredArgsConstructor
@Validated
public class ChatController {

    private final ChatService chatService;

    @PostMapping(value = "/{documentId}/query", version = "v1")
    public ResponseEntity<QueryResponse> getResponse(
            @RequestBody String query, @PathVariable UUID documentId,
            @RequestParam(required = false, defaultValue = "NVIDIA") Provider provider,
            @RequestHeader("conversationId") UUID conversationId) {
        final QueryResponse response = chatService.getResponseFromAssistant(provider, query, conversationId, documentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/{documentId}/query/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE, version = "v1")
    public Flux<ServerSentEvent<String>> streamChat(
            @RequestBody String query, @PathVariable UUID documentId,
            @RequestParam(required = false, defaultValue = "NVIDIA") Provider provider,
            @RequestHeader("X-Conversation-ID") UUID conversationId) {
        return chatService.getStreamResponseFromAssistant(provider, query, conversationId, documentId)
                .map(chunk -> ServerSentEvent.<String>builder().data(chunk).build());
    }

}
