package com.pranit.rag.service;

import com.pranit.rag.pipeline.factory.DocumentPipelineFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl {

    private final DocumentPipelineFactory factory;
    @Value("classpath:file/java-knowledge.json")
    private Resource resource;

    public void performEtlProcessOnFile() throws IOException {
        factory.getPipeline(resource);
    }
}
