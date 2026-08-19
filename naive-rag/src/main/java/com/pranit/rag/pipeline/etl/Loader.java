package com.pranit.rag.pipeline.etl;

import org.springframework.ai.document.Document;

import java.util.List;

public interface Loader {

    void load(List<Document> documents);
}
