package com.pranit.rag.document.service;

import java.util.UUID;

public interface DocumentStatusService {

    void markProcessing(UUID documentId);

    void markFailed(UUID documentId);
}
